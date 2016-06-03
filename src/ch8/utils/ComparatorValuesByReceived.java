package ch8.utils;

import java.util.Comparator;

import ch8.entity.TrafficInfo;

public class ComparatorValuesByReceived implements Comparator<TrafficInfo> {

	@Override
	public int compare(TrafficInfo lhs, TrafficInfo rhs) {
		// TODO Auto-generated method stub
		long l=lhs.getReceived();
		long r=rhs.getReceived();
		int result=0;
		if(l>r){
			result=-1;
		}else if(l<r){
			result=1;
		}
		
		return result;
	}

	
	
}
