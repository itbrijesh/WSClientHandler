package com.project.client;

import com.project.ws.ServerInfo;
import com.project.ws.ServerInfoService;

public class WSClient {

	public static void main( String ...args )
	{
		ServerInfoService service = new ServerInfoService();
		ServerInfo port = service.getServerInfoPort();
		
		System.out.println( " WS Client >> Server Name ::: " + port.getServerName() );
	}
}
