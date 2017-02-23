package com.project.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.soap.SOAPFaultException;

public class Utility {

	public static void generateSOAPErrMessage(SOAPMessage msg, String reason) {
	       try {
	          SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
	          SOAPFault soapFault = soapBody.addFault();
	          soapFault.setFaultString(reason);
	          throw new SOAPFaultException(soapFault); 
	       }
	       catch(SOAPException e) { }
	    }

	public static String getMACAddress()
	{
		InetAddress ip;
		StringBuilder sb = new StringBuilder();
			
		try {
				
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();
				
			System.out.print("Current MAC address : " + mac );
				
			for (int i = 0; i < mac.length; i++) {
					
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
					
			}
			System.out.println(sb.toString());
				
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
		} catch (SocketException e){
				
			e.printStackTrace();
				
		}
			
		return sb.toString();
	}
	
	public static void main( String ...args ) {
		getMACAddress();
	}
}
