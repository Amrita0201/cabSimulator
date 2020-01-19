package com.cab;

public class Point {

	private int lattitude,longitude;
	
	Point(int x, int y){
		this.lattitude=x;
		this.longitude=y;
	}
	
	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLattitude() {
		return this.lattitude;
	}
	public int getLongitude() {
		 return this.longitude;
	}
}
