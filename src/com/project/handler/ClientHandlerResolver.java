package com.project.handler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class ClientHandlerResolver implements HandlerResolver {

	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {

		List<Handler> list = new ArrayList<>();
		list.add( new MACAddressInjectHandler() );
		list.add( new ClientLogicalHander() );
		list.add( new WSSecurityClientHandler() );
		
		return list;
	}

}
