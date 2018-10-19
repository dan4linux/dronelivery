/**
 * This class holds the delivery information
 * Expected origin data
 * "WM001 N11W5 05:11:50"
 *  order identifier, followed by a space, 
 *  the customer's grid coordinate, another space, 
 *  and finally the order timestamp
 */
package net.swansonstuff.dronlivery.delivery;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.algorithms.AlgorithmManager;
import net.swansonstuff.dronlivery.utils.TimeUtils;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Delivery implements Comparable<Delivery>{
	
	transient private static final Logger LOG = LoggerFactory.getLogger(Delivery.class);

	private static final char SPACE = ' ';
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(System.getProperty("date.format","HH:mm:ss"));
	private static final Date EPOCH = new Date(0);
	
	private String orderInfo;
	private GridLocation gridLocation;
	private Date orderTime;
	private Date orderOutTime = EPOCH;
	private int transitTime;
	private CustomerType customerType = CustomerType.UNKNOWN;

	/**
	 * Expected Constructor 
	 * @param timeString 
	 * @param gridLocation 
	 * @param orderInfo 
	 */
	public Delivery(String orderInfo, String gridLocationString, String timeString) {
		this.orderInfo = String.valueOf(orderInfo);
		this.gridLocation = GridLocationFactory.create(gridLocationString);
		this.transitTime = TimeUtils.calcDeliveryTime(gridLocation);
		this.orderTime = TimeUtils.parseTimeString(timeString);
	}
	
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
	public GridLocation getGridLocation() {
		return gridLocation;
	}

	/**
	 * @param gridLocation the gridLocation to set
	 */
	public void setGridLocation(GridLocation gridLocation) {
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
	public void setOrderOutTime(long orderTime) {
		setOrderOutTime(new Date(orderTime));
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
	public int getTransitTime() {
		return transitTime;
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
	
	/**
	 * @return the orderOutTime
	 */
	public Date getOrderOutTime() {
		return orderOutTime;
	}

	/**
	 * @param orderOutTime the orderOutTime to set
	 */
	public void setOrderOutTime(Date orderOutTime) {
		this.orderOutTime = orderOutTime;
	}

	@Override
	public int compareTo(Delivery otherDelivery) {
		if (otherDelivery == null) {
			return -1;
		}
		
		return AlgorithmManager.getDefault().compare(this, otherDelivery);
	}
	
	@Override
	public String toString() {
		LOG.debug("[orderInfo:{}, gridLocation:{}, transitTime:{}, orderTime:{}, orderOutTime:{}]", 
				orderInfo, gridLocation, transitTime, orderTime, DATE_FORMATTER.format(orderOutTime));
		return new StringWriter().append(orderInfo).append(SPACE).append(DATE_FORMATTER.format(orderOutTime)).toString();
	}
}
