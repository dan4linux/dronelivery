package net.swansonstuff.dronlivery;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import net.swansonstuff.dronlivery.delivery.DeliveryProcessor;
import net.swansonstuff.dronlivery.delivery.algorithms.AlgorithmManager;

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
		try {
			new DeliveryProcessor(args[0], null).run(AlgorithmManager.getInstance());			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void usage() {
		System.err.println("Usage: "+getClass()+" </path/to/input/file>\n\nSettable Properties:");
		System.err.println("  \"date.format=HH:mm:ss\"");
		System.err.println("  \"delivery.field.delimiter= \"");
		System.err.println("  \"start.hour=6\"");
		System.err.println("  \"drones=1\"");
		System.err.println("  \"handling.time.millis=1000\"");
		System.exit(1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Dronlivery().run(args);
	}

}
