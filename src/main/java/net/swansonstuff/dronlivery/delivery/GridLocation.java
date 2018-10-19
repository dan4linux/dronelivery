package net.swansonstuff.dronlivery.delivery;

public class GridLocation {

	private char horizontalDirection;
	private char verticalDirection;
	private int horizontalDistance;
	private int verticalDistance;
	
	/**
	 * Standard constructor
	 * @param gridLocationString
	 */
	protected GridLocation(String gridLocationString) {
		int x = 0;
		StringBuffer sb = new StringBuffer();
		int strLen = gridLocationString.length();

		this.verticalDirection = gridLocationString.charAt(x++);
		do {
			sb.append(gridLocationString.charAt(x++));
		} while (Character.isDigit(gridLocationString.charAt(x)));
		this.verticalDistance = Integer.parseInt(sb.toString());

		sb.setLength(0);
		this.horizontalDirection = gridLocationString.charAt(x++);
		do {
			sb.append(gridLocationString.charAt(x++));
		} while (x < strLen && Character.isDigit(gridLocationString.charAt(x)));

		this.horizontalDistance = Integer.parseInt(sb.toString());
	}
	
	/**
	 * @return the horizontalDirection
	 */
	public char getHorizontalDirection() {
		return horizontalDirection;
	}

	/**
	 * @param horizontalDirection the horizontalDirection to set
	 */
	public void setHorizontalDirection(char horizontalDirection) {
		this.horizontalDirection = horizontalDirection;
	}

	/**
	 * @return the verticalDirection
	 */
	public char getVerticalDirection() {
		return verticalDirection;
	}

	/**
	 * @param verticalDirection the verticalDirection to set
	 */
	public void setVerticalDirection(char verticalDirection) {
		this.verticalDirection = verticalDirection;
	}

	/**
	 * @return the horizontalDistance
	 */
	public int getHorizontalDistance() {
		return horizontalDistance;
	}

	/**
	 * @param horizontalDistance the horizontalDistance to set
	 */
	public void setHorizontalDistance(int horizontalDistance) {
		this.horizontalDistance = horizontalDistance;
	}

	/**
	 * @return the verticaDistance
	 */
	public int getVerticalDistance() {
		return verticalDistance;
	}

	/**
	 * @param verticaDistance the verticaDistance to set
	 */
	public void setVerticalDistance(int verticaDistance) {
		this.verticalDistance = verticaDistance;
	}
	
	@Override
	public String toString() {
		return new StringBuffer().append(verticalDirection).append(verticalDistance)
				.append(horizontalDirection).append(horizontalDistance).toString();
	}
}
