package tech.lemnis.backgrounddataservicetester.models;


import org.joda.time.DateTime;

public class ProcessingTime {
    int viewId;
    DateTime start;
    DateTime end;

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }
}
