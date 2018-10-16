package net.swansonstuff.dronlivery;

import net.swansonstuff.dronlivery.delivery.DeliveryProcessor;

/**
 * Copyright Dan Swanson 2018
 * 
 *  This program is a drone launch scheduling program that 
 *  maximizes the net promoter score (NPS) amongst drone-delivery customers.
 *  
 *  It reads in a file given on the command line and processes 
 *  that file according to the PDF spec in this package.
 *  
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Dronlivery {
	
	public void run(String[] args) {
		if (args == null || args[0] == null) {
			usage();
		}
		
		new DeliveryProcessor(args[0]).run();
	}

	private void usage() {
		System.out.println("Usage: "+getClass()+" </path/to/input/file>");
		System.exit(1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Dronlivery().run(args);
	}

}
