package com.project.handler;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;

public class WSClientCallbackHandler implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		
		try {
			
			for( Callback callback: callbacks ) {
				
				if( callback instanceof UsernameCallback ) {
					
					UsernameCallback unameCallBack = (UsernameCallback) callback;
					unameCallBack.setUsername( "Pramukh" );
				}
				else if( callback instanceof PasswordCallback ){
					
					PasswordCallback passCallBack = (PasswordCallback) callback;
					passCallBack.setPassword( "password" );
				}
			}
			
		}
		catch( Exception e ) {
			e.printStackTrace();
			throw new RuntimeException( e.getMessage() );
		}
	}

}
