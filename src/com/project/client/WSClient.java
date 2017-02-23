package com.project.client;

import com.project.ws.ServerInfo;
import com.project.ws.ServerInfoService;

public class WSClient {

	public static void main( String ...args )
	{
		ServerInfoService service = new ServerInfoService();
		ServerInfo port = service.getServerInfoPort();
		
		System.out.println( " WS Client >> Server Name ::: " + port.getServerName() );
		
		//System.out.println( " Division >> "+ port.division( -10, 1 ) ); 
		
		
		/*
		// Add Request Headers
		Map<String,Object> myHeaders = new HashMap<>();
		myHeaders.put("Header_key1", "Header_value1");
		myHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
		
		( (BindingProvider) port ).getRequestContext().put( MessageContext.HTTP_REQUEST_HEADERS, myHeaders );
		
		// Fetch Request Headers
		Map<String, Object> responseMap = ( (BindingProvider) port ).getResponseContext();
		
		Set<String> keys = responseMap.keySet();

		for( Object key: keys ) {
			
			if( responseMap.get(key) instanceof Map ){
				
				Set temp = ( (Map) responseMap.get(key)).keySet();
				
				for( Object tkey : temp ) {
					System.out.println( tkey + " :: " + ( (Map) responseMap.get(key)).get(tkey) );
				}
			}
		}
		
		*/
	}
}
