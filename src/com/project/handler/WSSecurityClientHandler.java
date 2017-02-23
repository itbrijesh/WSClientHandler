package com.project.handler;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;

public class WSSecurityClientHandler implements SOAPHandler<SOAPMessageContext>{

	private XWSSProcessor processor = null;
	
	// First initialize the web service processor object.
	public WSSecurityClientHandler() {
		
		XWSSProcessorFactory factory = null;
		
		try {
			
			factory = XWSSProcessorFactory.newInstance();
			
			processor = factory.createProcessorForSecurityConfiguration( 
										new FileInputStream( new File("src/client.xml") ), 
										new WSClientCallbackHandler() );
		}
		catch( Exception e ) { throw new RuntimeException( e.getMessage() ); }
	}
	
	/* This method will modify the existing message by making it secured message */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		 
		Boolean outbond = (Boolean) context.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );
		
		SOAPMessage message = context.getMessage();
		System.out.println( "WSSecurityClientHandler::: Normal Message >> " + message );
		
		if( outbond ) {
			
			try {
			
				ProcessingContext processingContext = processor.createProcessingContext( message );
				processingContext.setSOAPMessage( message );
				
				SOAPMessage secureMessage = processor.secureOutboundMessage( processingContext );
				context.setMessage( secureMessage );
				System.out.println( "WSSecurityClientHandler::: Secured Message >> " + secureMessage );
				
			} 
			catch (XWSSecurityException e) {
				e.printStackTrace();
				throw new RuntimeException( e.getMessage() );
			}
			
		}
		return true;
	}

	/* This method will add security headers MustUnderstands=1 */
	@Override
	public Set<QName> getHeaders() {
		 
		String uri = "http://docs.oasis-open.org/wss/2004/01/"
				+ "oasis-200401-wss-wssecurity-secext-1.0.xsd";
		
		QName qname = new QName( uri, "Security", "wsse");
		Set<QName> headers = new HashSet<>();
		headers.add( qname );
		
		return headers;
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		 return true;
	}

	@Override
	public void close(MessageContext context) {
		  
	}

	

}
