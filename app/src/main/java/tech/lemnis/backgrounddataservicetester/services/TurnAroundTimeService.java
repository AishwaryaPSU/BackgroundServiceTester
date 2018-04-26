package tech.lemnis.backgrounddataservicetester.services;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.HashMap;

import tech.lemnis.backgrounddataservicetester.models.ProcessingTime;

public class TurnAroundTimeService {
    HashMap<Integer,ProcessingTime> viewProcessingTimes;
    public TurnAroundTimeService(){
        viewProcessingTimes = new HashMap<>();
    }
    public void recordStart(int viewId) {
        ProcessingTime processingTime = new ProcessingTime();
        if(viewProcessingTimes.containsKey(viewId)) {
            processingTime = viewProcessingTimes.get(viewId);
        }
        processingTime.setStart(new DateTime());
        viewProcessingTimes.put(viewId, processingTime);
    }
    public void recordStop(int viewId){
        ProcessingTime processingTime = new ProcessingTime();
        if(viewProcessingTimes.containsKey(viewId)) {
            processingTime = viewProcessingTimes.get(viewId);
        }
        processingTime.setEnd(new DateTime());
        viewProcessingTimes.put(viewId, processingTime);
    }
    public Duration getDuration(int viewId){
        ProcessingTime processingTime  = viewProcessingTimes.get(viewId);
        if(processingTime != null){
            DateTime start = processingTime.getStart();
            DateTime end = processingTime.getEnd();
            return  new Duration(start,end);
        }
        return Duration.ZERO;
    }
}
