/**
 * This class holds the delivery information
 * Expected origin data
 * "WM001 N11W5 05:11:50"
 *  order identifier, followed by a space, 
 *  the customer's grid coordinate, another space, 
 *  and finally the order timestamp
 */
package net.swansonstuff.dronlivery.delivery;

import java.util.Date;

import net.swansonstuff.dronlivery.utils.TimeUtils;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Delivery implements Comparable<Delivery>{

	private String orderInfo;
	private String gridLocation;
	private Date orderTime;

	/**
	 * Expected Constructor 
	 * @param timeString 
	 * @param gridLocation 
	 * @param orderInfo 
	 */
	public Delivery(String orderInfo, String gridLocation, String timeString) {
		this.orderInfo = orderInfo;
		this.gridLocation = gridLocation;
		this.deliveryTime = TimeUtils.calcDeliveryTime(gridLocation);
		this.orderTime = TimeUtils.parse(timeString);
	}
	
	private int deliveryTime;
	private CustomerType customerType = CustomerType.UNKNOWN;
	/**
	 * @return the orderInfo
	 */
	public String getOrderInfo() {
		return orderInfo;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @return the gridLocation
	 */
	public String getGridLocation() {
		return gridLocation;
	}

	/**
	 * @param gridLocation the gridLocation to set
	 */
	public void setGridLocation(String gridLocation) {
		this.gridLocation = gridLocation;
	}

	/**
	 * @return the orderTime
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * Time to deliver in millis
	 * @return millis required to deliver the package
	 */
	public int getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * @return the customerType
	 */
	public CustomerType getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	@Override
	public int compareTo(Delivery otherDelivery) {
		if (otherDelivery == null) {
			return -1;
		}
		
		int myDeliveryTime = getDeliveryTime();
		int theirDeliveryTime = otherDelivery.getDeliveryTime();
		if (myDeliveryTime == theirDeliveryTime) {
			return 0;
		}
		
		return (myDeliveryTime > theirDeliveryTime)?1:-1;
	}
}
