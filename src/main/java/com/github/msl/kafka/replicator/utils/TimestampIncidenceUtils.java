package com.github.msl.kafka.replicator.utils;

import java.sql.Timestamp;

import com.github.msl.kafka.replicator.config.ReplicatorConfig;
import com.github.msl.kafka.replicator.dto.TimeDTO;

public class TimestampIncidenceUtils {
	
	private TimestampIncidenceUtils() {}
	
	public static long getRealTimestampIncidence(Long incidenceTimestamp) {
		return ReplicatorConfig.TIMESTAMP_0101198012PM + (incidenceTimestamp * 1000);
	}
	
	public static TimeDTO diffTimestampsSeconds(Timestamp t1, Timestamp t2) {
		return diffTimestampsSeconds(t1.getTime() , t2.getTime() );
	}
	
	public static TimeDTO diffTimestampsSeconds(long t1, long t2) {
		long milliseconds = t2 - t1;
		int seconds = (int) milliseconds / 1000;

		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = (seconds % 3600) % 60;
		
		TimeDTO timeDto = TimeDTO.builder().hours(hours).minutes(minutes).seconds(seconds).millis(milliseconds).build();
		return timeDto;
	}
	

}

