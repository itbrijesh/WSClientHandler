package com.project.handler;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import com.project.ws.Division;

public class ClientLogicalHander implements LogicalHandler<LogicalMessageContext> {

	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		
		System.out.println( "Logical Handler Start ---------------------------------------" );
		
		// This flag will check for the incoming message or outgoing message. 
		Boolean outbond = (Boolean) context.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );
		
		if( outbond ) {
			
			LogicalMessage message = context.getMessage();
			
			try {
				
				JAXBContext clientSoap = JAXBContext.newInstance( "com.project.ws" ); // package of client stub.
				Object payload = message.getPayload( clientSoap );
				
				if( payload instanceof JAXBElement ) {
					
					Object value = ((JAXBElement) payload).getValue();
					System.out.println( "Payload value ::: " + value.toString() );
					
					if( value.toString().toLowerCase().contains("division") ) {
						
						int a = ( (Division) value).getArg0();
						int b = ( (Division) value).getArg1();
						
						System.out.println( "Original Values :::: " + a + " , " + b );
						
						// If value zero throw exception
						if( b == 0 ) {
							throw new RuntimeException("Divided by value cannot be Zero!!!");
						}
						
						// If value -ve make it +ve and set the value
						if( a < 0 || b < 0)
						{
							System.out.println( "Value set by the client : " + a + " , " + b );
							
							( ( Division ) value ).setArg0( Math.abs( a ) );
							( ( Division ) value ).setArg1( Math.abs( b ) );
							
							System.out.println("Value updated by the Logical Handler : " + 
												Math.abs( a ) + " , " + Math.abs(b) );
						}
						
						// Update the payload
						((JAXBElement) payload ).setValue( value );
						
						// Update the message
						message.setPayload( payload, clientSoap );
					}
				}
				else {
					System.out.println( "Payload is not an instance of JAXBElement..." );
				}
			}
			catch( Exception e ) { throw new RuntimeException( e.getMessage() ); }
		}
		
		System.out.println( "Logical Handler---- END ---------------------------------------" );
		
		return true;
	}

	@Override
	public boolean handleFault(LogicalMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("---ClientLogicalHandler---close---");
	}

}
