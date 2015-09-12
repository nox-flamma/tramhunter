# iView Web Service Debugging #

Some debugging of the web services calls

## GetNextPredictedRoutesCollection ##

Post:

```
POST /pidsservice/pids.asmx HTTP/1.1
Host: ws.tramtracker.com.au
User-Agent: tramTRACKER/1.3.2.1 CFNetwork/445.6 Darwin/10.0.0d3
Soapaction: http://www.yarratrams.com.au/pidsservice/GetNextPredictedRoutesCollection
Content-Type: text/xml;charset=utf-8
Accept: */*
Accept-Language: en-us
Accept-Encoding: gzip, deflate
Content-Length: 749
Connection: keep-alive
```
```
<?xml version="1.0" encoding="utf-16"?>
<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
    <soap12:Header>
        <PidsClientHeader xmlns="http://www.yarratrams.com.au/pidsservice/">
            <ClientGuid>51044a36-d9ea-45ab-b94f-7b4bf98d5099</ClientGuid>
            <ClientType>IPHONEPID</ClientType>
            <ClientWebServiceVersion>6.4.0.0</ClientWebServiceVersion>
            <ClientVersion>1.3.2.1</ClientVersion>
        </PidsClientHeader>
    </soap12:Header>
    <soap12:Body>
        <GetNextPredictedRoutesCollection xmlns="http://www.yarratrams.com.au/pidsservice/">
            <stopNo>3070</stopNo>
            <lowFloor>false</lowFloor>
            <routeNo>0</routeNo>
        </GetNextPredictedRoutesCollection>
    </soap12:Body>
</soap12:Envelope>
```

Reply:

```
HTTP/1.1 200 OK
Connection: Keep-Alive
Content-Length: 5921
Date: Wed, 02 Jun 2010 19:38:40 GMT
Content-Type: application/soap+xml; charset=utf-8
Server: Microsoft-IIS/7.0
Cache-Control: private, max-age=0
X-AspNet-Version: 2.0.50727
X-Powered-By: ASP.NET
```
```
<?xml version="1.0" encoding="utf-16"?>
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soap:Body>
        <GetNextPredictedRoutesCollectionResponse xmlns="http://www.yarratrams.com.au/pidsservice/">
            <GetNextPredictedRoutesCollectionResult>
                <xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata">
                    <xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:MainDataTable="ToReturn" msdata:UseCurrentLocale="true">
                        <xs:complexType>
                            <xs:choice minOccurs="0" maxOccurs="unbounded">
                                <xs:element name="ToReturn">
                                    <xs:complexType>
                                        <xs:sequence>
                                            <xs:element name="TripID" type="xs:int" minOccurs="0" />
                                            <xs:element name="InternalRouteNo" type="xs:short" minOccurs="0" />
                                            <xs:element name="RouteNo" type="xs:string" minOccurs="0" />
                                            <xs:element name="HeadboardRouteNo" type="xs:string" minOccurs="0" />
                                            <xs:element name="VehicleNo" type="xs:short" minOccurs="0" />
                                            <xs:element name="Destination" type="xs:string" default="" minOccurs="0" />
                                            <xs:element name="HasDisruption" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="IsTTAvailable" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="IsLowFloorTram" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="AirConditioned" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="DisplayAC" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="HasSpecialEvent" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="SpecialEventMessage" type="xs:string" minOccurs="0" />
                                            <xs:element name="PredictedArrivalDateTime" type="xs:dateTime" default="9999-12-31T23:59:59.9999999+11:00" minOccurs="0" />
                                            <xs:element name="RequestDateTime" type="xs:dateTime" minOccurs="0" />
                                        </xs:sequence>
                                    </xs:complexType>
                                </xs:element>
                            </xs:choice>
                        </xs:complexType>
                    </xs:element>
                </xs:schema>
                <diffgr:diffgram xmlns:msdata="urn:schemas-microsoft-com:xml-msdata" xmlns:diffgr="urn:schemas-microsoft-com:xml-diffgram-v1">
                    <DocumentElement xmlns="">
                        <ToReturn diffgr:id="ToReturn1" msdata:rowOrder="0">
                            <InternalRouteNo>55</InternalRouteNo>
                            <RouteNo>55</RouteNo>
                            <HeadboardRouteNo>55</HeadboardRouteNo>
                            <VehicleNo>161</VehicleNo>
                            <Destination>West Coburg</Destination>
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>true</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>false</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage />
                            <PredictedArrivalDateTime>2010-06-03T06:36:00+10:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-03T05:38:40.6269975+10:00</RequestDateTime>
                        </ToReturn>
                        <ToReturn diffgr:id="ToReturn2" msdata:rowOrder="1">
                            <InternalRouteNo>55</InternalRouteNo>
                            <RouteNo>55</RouteNo>
                            <HeadboardRouteNo>55</HeadboardRouteNo>
                            <VehicleNo>229</VehicleNo>
                            <Destination>West Coburg</Destination>
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>true</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>false</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage />
                            <PredictedArrivalDateTime>2010-06-03T06:49:00+10:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-03T05:38:40.6269975+10:00</RequestDateTime>
                        </ToReturn>
                        <ToReturn diffgr:id="ToReturn3" msdata:rowOrder="2">
                            <InternalRouteNo>55</InternalRouteNo>
                            <RouteNo>55</RouteNo>
                            <HeadboardRouteNo>55</HeadboardRouteNo>
                            <VehicleNo>200</VehicleNo>
                            <Destination>West Coburg</Destination>
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>true</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>false</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage />
                            <PredictedArrivalDateTime>2010-06-03T07:01:00+10:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-03T05:38:40.6269975+10:00</RequestDateTime>
                        </ToReturn>
                        <ToReturn diffgr:id="ToReturn4" msdata:rowOrder="3">
                            <InternalRouteNo>59</InternalRouteNo>
                            <RouteNo>59</RouteNo>
                            <HeadboardRouteNo>59</HeadboardRouteNo>
                            <VehicleNo>2110</VehicleNo>
                            <Destination>Airport West</Destination>
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>true</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>true</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage />
                            <PredictedArrivalDateTime>2010-06-03T05:51:00+10:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-03T05:38:40.6269975+10:00</RequestDateTime>
                        </ToReturn>
                        <ToReturn diffgr:id="ToReturn5" msdata:rowOrder="4">
                            <InternalRouteNo>59</InternalRouteNo>
                            <RouteNo>59</RouteNo>
                            <HeadboardRouteNo>59</HeadboardRouteNo>
                            <VehicleNo>2092</VehicleNo>
                            <Destination>Airport West</Destination>
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>true</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>true</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage />
                            <PredictedArrivalDateTime>2010-06-03T06:06:00+10:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-03T05:38:40.6269975+10:00</RequestDateTime>
                        </ToReturn>
                        <ToReturn diffgr:id="ToReturn6" msdata:rowOrder="5">
                            <InternalRouteNo>59</InternalRouteNo>
                            <RouteNo>59</RouteNo>
                            <HeadboardRouteNo>59</HeadboardRouteNo>
                            <VehicleNo>2048</VehicleNo>
                            <Destination>Airport West</Destination>
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>true</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>true</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage />
                            <PredictedArrivalDateTime>2010-06-03T06:18:00+10:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-03T05:38:40.6269975+10:00</RequestDateTime>
                        </ToReturn>
                    </DocumentElement>
                </diffgr:diffgram>
            </GetNextPredictedRoutesCollectionResult>
            <validationResult />
        </GetNextPredictedRoutesCollectionResponse>
    </soap:Body>
</soap:Envelope>
```



## Special Event ##
```
<?xml  version="1.0" encoding="utf-16"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soap:Body>
        <GetNextPredictedRoutesCollectionResponse xmlns="http://www.yarratrams.com.au/pidsservice/">
            <GetNextPredictedRoutesCollectionResult>
                <xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata">
                    <xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:MainDataTable="ToReturn" msdata:UseCurrentLocale="true">
                        <xs:complexType>
                            <xs:choice minOccurs="0" maxOccurs="unbounded">
                                <xs:element name="ToReturn">
                                    <xs:complexType>
                                        <xs:sequence>
                                            <xs:element name="TripID" type="xs:int" minOccurs="0" />
                                            <xs:element name="InternalRouteNo" type="xs:short" minOccurs="0" />
                                            <xs:element name="RouteNo" type="xs:string" minOccurs="0" />
                                            <xs:element name="HeadboardRouteNo" type="xs:string" minOccurs="0" />
                                            <xs:element name="VehicleNo" type="xs:short" minOccurs="0" />
                                            <xs:element name="Destination" type="xs:string" default="" minOccurs="0" />
                                            <xs:element name="HasDisruption" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="IsTTAvailable" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="IsLowFloorTram" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="AirConditioned" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="DisplayAC" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="HasSpecialEvent" type="xs:boolean" default="false" minOccurs="0" />
                                            <xs:element name="SpecialEventMessage" type="xs:string" minOccurs="0" />
                                            <xs:element name="PredictedArrivalDateTime" type="xs:dateTime" default="9999-12-31T23:59:59.9999999+11:00" minOccurs="0" />
                                            <xs:element name="RequestDateTime" type="xs:dateTime" minOccurs="0" />
                                        </xs:sequence>
                                    </xs:complexType>
                                </xs:element>
                            </xs:choice>
                        </xs:complexType>
                    </xs:element>
                </xs:schema>
                <diffgr:diffgram xmlns:msdata="urn:schemas-microsoft-com:xml-msdata" xmlns:diffgr="urn:schemas-microsoft-com:xml-diffgram-v1">
                    <DocumentElement xmlns="">
                        <ToReturn diffgr:id="ToReturn1" msdata:rowOrder="0">
                            <InternalRouteNo>0</InternalRouteNo>
                            <RouteNo>0</RouteNo>
                            <HeadboardRouteNo>0</HeadboardRouteNo>
                            <VehicleNo>-1</VehicleNo>
                            <Destination />
                            <HasDisruption>false</HasDisruption>
                            <IsTTAvailable>false</IsTTAvailable>
                            <IsLowFloorTram>false</IsLowFloorTram>
                            <AirConditioned>false</AirConditioned>
                            <DisplayAC>false</DisplayAC>
                            <HasSpecialEvent>false</HasSpecialEvent>
                            <SpecialEventMessage>tramTRACKER unavailable due to bus replacement on Routes 3a, 5, 16, 64 &amp; 67 until last tram Monday.</SpecialEventMessage>
                            <PredictedArrivalDateTime>9999-12-31T23:59:59.9999999+11:00</PredictedArrivalDateTime>
                            <RequestDateTime>2010-06-12T05:39:30.6255981+10:00</RequestDateTime>
                        </ToReturn>
                    </DocumentElement>
                </diffgr:diffgram>
            </GetNextPredictedRoutesCollectionResult>
            <validationResult />
        </GetNextPredictedRoutesCollectionResponse>
    </soap:Body>
</soap:Envelope> 
```


## GetStopInformation ##

Post:
```
POST /pidsservice/pids.asmx HTTP/1.1
Host: ws.tramtracker.com.au
Accept: */*
User-Agent: TramHunter
SOAPAction: http://www.yarratrams.com.au/pidsservice/GetStopInformation
Content-Type: text/xml
Connection: close
Content-Length: 667
```
```
<?xml  version="1.0" encoding="utf-16"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Header>
        <PidsClientHeader xmlns="http://www.yarratrams.com.au/pidsservice/">
            <ClientGuid>00000000-0000-0000-0000-000000000001</ClientGuid>
            <ClientType>DASHBOARDWIDGET</ClientType>
            <ClientVersion>1.0.0</ClientVersion>
            <ClientWebServiceVersion>6.1.0.0</ClientWebServiceVersion>
        </PidsClientHeader>
    </soap:Header>
    <soap:Body>
        <GetStopInformation xmlns="http://www.yarratrams.com.au/pidsservice/">
            <stopNo>1110</stopNo>
        </GetStopInformation>
    </soap:Body>
</soap:Envelope> 
```


```
 <?xml  version="1.0" encoding="utf-16"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soap:Body>
        <GetStopInformationResponse xmlns="http://www.yarratrams.com.au/pidsservice/">
            <GetStopInformationResult>
                <xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata">
                    <xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:MainDataTable="StopInformation" msdata:UseCurrentLocale="true">
                        <xs:complexType>
                            <xs:choice minOccurs="0" maxOccurs="unbounded">
                                <xs:element name="StopInformation">
                                    <xs:complexType>
                                        <xs:sequence>
                                            <xs:element name="FlagStopNo" type="xs:string" minOccurs="0" />
                                            <xs:element name="StopName" type="xs:string" minOccurs="0" />
                                            <xs:element name="CityDirection" type="xs:string" minOccurs="0" />
                                            <xs:element name="Latitude" type="xs:double" minOccurs="0" />
                                            <xs:element name="Longitude" type="xs:double" minOccurs="0" />
                                            <xs:element name="SuburbName" type="xs:string" minOccurs="0" />
                                            <xs:element name="IsCityStop" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="HasConnectingBuses" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="HasConnectingTrains" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="HasConnectingTrams" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="StopLength" type="xs:short" minOccurs="0" />
                                            <xs:element name="IsPlatformStop" type="xs:boolean" minOccurs="0" />
                                            <xs:element name="Zones" type="xs:string" minOccurs="0" />
                                        </xs:sequence>
                                    </xs:complexType>
                                </xs:element>
                            </xs:choice>
                        </xs:complexType>
                    </xs:element>
                </xs:schema>
                <diffgr:diffgram xmlns:msdata="urn:schemas-microsoft-com:xml-msdata" xmlns:diffgr="urn:schemas-microsoft-com:xml-diffgram-v1">
                    <DocumentElement xmlns="">
                        <StopInformation diffgr:id="StopInformation1" msdata:rowOrder="0">
                            <FlagStopNo>40</FlagStopNo>
                            <StopName>Wattletree Rd &amp; Dandenong Rd</StopName>
                            <CityDirection>towards City</CityDirection>
                            <Latitude>-37.8608487238043</Latitude>
                            <Longitude>145.014850456452</Longitude>
                            <SuburbName>Armadale</SuburbName>
                            <IsCityStop>false</IsCityStop>
                            <HasConnectingBuses>false</HasConnectingBuses>
                            <HasConnectingTrains>false</HasConnectingTrains>
                            <HasConnectingTrams>true</HasConnectingTrams>
                            <StopLength>24</StopLength>
                            <IsPlatformStop>true</IsPlatformStop>
                            <Zones>1</Zones>
                        </StopInformation>
                    </DocumentElement>
                </diffgr:diffgram>
            </GetStopInformationResult>
            <validationResult />
        </GetStopInformationResponse>
    </soap:Body>
</soap:Envelope> 
```