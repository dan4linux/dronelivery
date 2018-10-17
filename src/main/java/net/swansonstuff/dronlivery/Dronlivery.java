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
		if (args == null || args.length == 0) {
			usage();
		}
		
		new DeliveryProcessor(args[0], System.out).run();
	}

	private void usage() {
		System.out.println("Usage: "+getClass()+" </path/to/input/file>\n\nSettable Properties:");
		System.out.println("  \"date.format=HH:mm:ss\"");
		System.out.println("  \"delivery.field.delimiter= \"");
		System.out.println("  \"start.hour=6\"");
		System.out.println("  \"drones=1\"");
		System.out.println("  \"handling.time.millis=1000\"");
		System.exit(1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Dronlivery().run(args);
	}

}
