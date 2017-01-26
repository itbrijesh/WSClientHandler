package com.project.handler;

import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.project.utils.Utility;

public class MACAddressInjectHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		System.out.println("Client ::: Handle Message...START...");
		
		Boolean outgoingRequest = (Boolean) context.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );
		
		if( outgoingRequest )
		{
			try {
				
				SOAPMessage message = context.getMessage();
				SOAPEnvelope envelop = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelop.getHeader();
				
				// If no header then add new header.
				if( header == null )
				{
					header = envelop.addHeader();
				}
			
				// Get MAC address.
				String mac = Utility.getMACAddress();
				
				//add a soap header, name as "mac address"
	            QName qname = new QName("http://ws.project.com/", "macAddress");
	            SOAPHeaderElement element = header.addHeaderElement(qname);

	            element.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
	            element.addTextNode(mac);
	            message.saveChanges();

	            message.writeTo(System.out);
				
			} 
			catch (SOAPException | IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Client ::: Handle Message...END...");
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("Client :: handleFault...");
		return true;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("Client :: close...");
	}

	@Override
	public Set<QName> getHeaders() {
		System.out.println("Client :: getHeaders...");
		return null;
	}

}
