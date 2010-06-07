package com.andybotting.tramhunter.activity;

import java.util.List;
import java.util.Vector;

import com.andybotting.tramhunter.R;
import com.andybotting.tramhunter.dao.TramHunterDB;
import com.andybotting.tramhunter.objects.Route;
import com.andybotting.tramhunter.objects.RouteGroup;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.ExpandableListView.OnChildClickListener;

public class RoutesListActivity extends ExpandableListActivity {

	//ListView listView;
	//Route selectedRoute;
	//Vector<Route> routes;
	
	List<RouteGroup> routeGroups;

	private ExpandableListAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);	  
		
		String title = "Routes";
		setTitle(title);
		
		getExpandableListView().setOnChildClickListener(new OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				
				int routeId = groupPosition * 2 + childPosition;
				
				Bundle bundle = new Bundle();
				bundle.putLong("routeId", routeId);
				Intent stopsListIntent = new Intent(RoutesListActivity.this, StopsListActivity.class);
				stopsListIntent.putExtras(bundle);
				startActivityForResult(stopsListIntent, 1);
				return true;
			}
		});
	 
		displayRoutes();
	}
	
	
		
	public void displayRoutes() {

		TramHunterDB db = new TramHunterDB(this);
		routeGroups = db.getRouteGroups();
				
		//listView = (ExpandableListView)this.findViewById(android.R.id.e);
		
		
		mAdapter = new MyExpandableListAdapter();
	
        // Set up our adapter
        
        setListAdapter(mAdapter);
	}
  
	
	
	
	
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {

        public Object getChild(int groupPosition, int childPosition) {
        	// Decide if it's routeUp or routeDown
        	if (childPosition == 0)
        		return routeGroups.get(groupPosition).getRouteUp();
            return routeGroups.get(groupPosition).getRouteDown();
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
        	// Always two routes for each routeGroup
            return 2;
        }

        
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 50);

            TextView textView = new TextView(RoutesListActivity.this);
            textView.setLayoutParams(lp);
            
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            
            textView.setPadding(40, 0, 0, 0);
            textView.setTextSize(20);
            
			RouteGroup rg = routeGroups.get(groupPosition);
			Route r;
			if (childPosition == 0) {
				r = rg.getRouteUp();
			}
			else {
				r = rg.getRouteDown();
			}

			textView.setText("To " + r.getDestination());
			
			return textView;
        }

        public Object getGroup(int groupPosition) {
            return routeGroups.get(groupPosition);
        }

        public int getGroupCount() {
            return routeGroups.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			View pv = convertView;
			ViewWrapper wrapper = null;
            
            if ( pv == null ) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    pv = inflater.inflate (R.layout.routes_row, null );
            }

			wrapper = new ViewWrapper(pv);
			pv.setTag(wrapper);

			RouteGroup rg = routeGroups.get(groupPosition);
				
			wrapper.getTextLine1().setText("Route " + rg.getNumber());
			wrapper.getTextLine2().setText(rg.getDestination());
			
			wrapper.getTextLine1().setPadding(32, 0, 0, 0);
			wrapper.getTextLine2().setPadding(32, 0, 0, 0);
			
			return pv;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
	

	
	
	
//	// http://code.google.com/p/alldroid/source/browse/trunk/AllDroid/src/org/alldroid/forum/data/AboutRouteGroupseExpandableListDataAdapter.java
//	
//	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
//
//        private List<RouteGroup> routeGroups;
//        private Context context;
//        //private BaseForumService service;
//
//        /**
//         *
//         */
//        public MyExpandableListAdapter ( final Activity context, ExpandableListView listview) {
//                this.setRouteGroups ( new ArrayList<RouteGroup> ( ) );
//                this.setContext ( context );
//                //this.setService ( service );
//
//
//                RouteGroup rg = new RouteGroup();
//                this.getRouteGroups().add((RouteGroup) rg);
//
//                listview.setAdapter ( this );
//                listview.setItemsCanFocus ( true );
//                listview.setChildIndicator ( null );
//                listview.setGroupIndicator ( null );
//                listview.setOnItemClickListener ( new OnItemClickListener ( ) {
//
//                        @Override
//                        public void onItemClick ( AdapterView<?> parent, View view, int position, long id ) {
//                                Log.d ("Testing", "Selecting item: " + Integer.toString ( position ) );
//                        }
//
//                } );
//        }
//
//        /**
//         * @param contributors
//         *          the contributors to set
//         */
//        protected void setRouteGroups (List<RouteGroup> routeGroups) {
//                this.routeGroups = routeGroups;
//        }
//
//        /**
//         * @return the routeGroups
//         */
//        public List<RouteGroup>getRouteGroups() {
//                return routeGroups;
//        }
//
//        /**
//         * @param context
//         *          the context to set
//         */
//        private void setContext(Context context) {
//                this.context = context;
//        }
//
//        /**
//         * @return the context
//         */
//        public Context getContext() {
//                return context;
//        }
//
////        /**
////         * @param service
////         *          the service to set
////         */
////        private void setService ( BaseForumService service ) {
////                this.service = service;
////        }
////
////        /**
////         * @return the service
////         */
////        public BaseForumService getService ( ) {
////                return service;
////        }
//
//        @Override
//        public RouteGroupInfo getChild ( int groupPosition, int childPosition ) {
//                return this.getGroup ( groupPosition ).getInfos().get(childPosition);
//        }
//
//        @Override
//        public long getChildId ( int groupPosition, int childPosition ) {
//                return childPosition;
//        }
//
//        @Override
//        public View getChildView ( int position, int childPosition, boolean isLastChild, View convertView, ViewGroup parent ) {
//			View pv = convertView;
//			ViewWrapper wrapper = null;
//            
//            if ( pv == null ) {
//                    LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    pv = inflater.inflate (R.layout.routes_row, null );
//            }
//
//			wrapper = new ViewWrapper(pv);
//			pv.setTag(wrapper);
//
//			Route thisRoute = routes.get(position);
//				
//			wrapper.getTextLine1().setText("Route " + thisRoute.getNumber());
//			wrapper.getTextLine2().setText("To " + thisRoute.getDestination());
//
//			return pv;
//
//
//        }
//
//        @Override
//        public int getChildrenCount ( int groupPosition ) {
//                return this.getGroup ( groupPosition ).getInfos ( ).size ( );
//        }
//
//
//        @Override
//        public RouteGroup getGroup ( int groupPosition ) {
//                return this.getRouteGroups ( ).get ( groupPosition );
//        }
//
//        @Override
//        public int getGroupCount ( ) {
//                return this.getRouteGroups ( ).size ( );
//        }
//
//        @Override
//        public long getGroupId ( int groupPosition ) {
//                return groupPosition;
//        }
//
//        @Override
//        public View getGroupView (int position, boolean isExpanded, View convertView, ViewGroup parent) {
//    			View pv = convertView;
//    			ViewWrapper wrapper = null;
//                
//                if ( pv == null ) {
//                        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        pv = inflater.inflate (R.layout.routes_row, null );
//                }
//
//    			wrapper = new ViewWrapper(pv);
//    			pv.setTag(wrapper);
//    
//    			Route thisRoute = routes.get(position);
//    				
//    			wrapper.getTextLine1().setText("Route " + thisRoute.getNumber());
//    			wrapper.getTextLine2().setText("To " + thisRoute.getDestination());
//
//    			return pv;
//        }
//
//        @Override
//        public boolean hasStableIds ( ) {
//                return false;
//        }
//
//        @Override
//        public boolean isChildSelectable ( int groupPosition, int childPosition ) {
//                return false;
//        }
//
//}

	
	
	
	
	
	
//	private class RoutesListAdapter extends BaseAdapter {
//		
//		private Context mContext;		
//
//		public RoutesListAdapter(Context context) {
//			mContext = context;
//		}
//
//		public int getCount() {
//			return routes.size();
//		}
//
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			
//			View pv = convertView;
//			ViewWrapper wrapper = null;
//
//			LayoutInflater inflater = getLayoutInflater();
//			pv = inflater.inflate(R.layout.routes_row, parent, false);
//					
//			wrapper = new ViewWrapper(pv);
//			pv.setTag(wrapper);
//
//			Route thisRoute = routes.get(position);
//				
//			wrapper.getRouteNumber().setText("Route " + thisRoute.getNumber());
//			wrapper.getRouteDestination().setText("To " + thisRoute.getDestination());
//
//			return pv;
//		}
//	}
//
	class ViewWrapper {
		View base;
			
		TextView textLine1 = null;
		TextView textLine2 = null;
			

		ViewWrapper(View base) {
			this.base = base;
		}

		TextView getTextLine1() {
			if (textLine1 == null) {
				textLine1 = (TextView) base.findViewById(R.id.textLine1);
			}
			return (textLine1);
		}

		TextView getTextLine2() {
			if (textLine2 == null) {
				textLine2 = (TextView) base.findViewById(R.id.textLine2);
			}
			return (textLine2);
		}

	}
	  
}
