package com.cab;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Track {
	private static Point[][] tracks= {
			{new Point(2,1),new Point(3,2),new Point(4,3), new Point(5,3)},
			{new Point(4,1),new Point(5,8),new Point(7,9), new Point(6,4)},
			{new Point(6,1),new Point(6,5),new Point(5,1), new Point(5,4)},
			{new Point(3,1),new Point(3,7),new Point(5,3), new Point(8,3)}
			};
	
	public static Point[] getMyTrack(Point p) {
		for(int i = 0;i<4;i++){
			if(Arrays.asList(tracks[i]).indexOf(p) > -1)
				return tracks[i];
		}
		return tracks[0];
	}

}
