package ch8.utils;

import java.util.Comparator;

import ch8.entity.TrafficInfo;

public class CompartorValuesByTransmitted implements Comparator<TrafficInfo> {

	@Override
	public int compare(TrafficInfo lhs, TrafficInfo rhs) {
		// TODO Auto-generated method stub
		long l=lhs.getTransmitted();
		long r=rhs.getTransmitted();
		int result=0;
		if(l>r){
			result=-1;
		}else if(l<r){
			result=1;
		}
		
		return result;
	}
}
