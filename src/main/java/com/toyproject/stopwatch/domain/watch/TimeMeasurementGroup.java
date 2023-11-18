package com.toyproject.stopwatch.domain.watch;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeMeasurementGroup {

    private final List<TimeMeasurement> timeMeasurements;

    public TimeMeasurementGroup(TimeMeasurement timeMeasurement) {
        this.timeMeasurements = new ArrayList<>();
        this.timeMeasurements.add(timeMeasurement);
    }


    public void changeStatus(LocalDateTime statusChangingTime) {

        final TimeMeasurement timeMeasurement = getLastMeasurement();
        timeMeasurement.endMeasuring(statusChangingTime);

        final MeasuringStatus nextMeasuringStatus = timeMeasurement.nextMeasuringStatus();

        this.timeMeasurements.add(new TimeMeasurement(statusChangingTime, nextMeasuringStatus));
    }

    private TimeMeasurement getLastMeasurement(){
        return this.timeMeasurements.get(recordsCount() - 1);
    }

    public int recordsCount() {
        return this.timeMeasurements.size();
    }

    public LocalTime getTotalStudyTime(LocalDateTime checkTime) {
        final List<TimeMeasurement> studyMeasurements = this.timeMeasurements.stream()
                .filter(measurement -> measurement.getMeasuringStatus() == MeasuringStatus.STUDY)
                .collect(Collectors.toList());

        List<LocalTime> measuredTimes = new ArrayList<>();
        for (TimeMeasurement studyMeasurement : studyMeasurements) {
            if(studyMeasurement.isMeasuringEnd()){
                measuredTimes.add(studyMeasurement.getTotalTime());
            } else {
                studyMeasurement.compareTime(checkTime);
            }
        }

        final Duration sumDuration = measuredTimes.stream()
                .map(localTime -> Duration.between(LocalTime.MIDNIGHT, localTime))
                .reduce(Duration.ZERO, Duration::plus);

        return LocalTime.ofSecondOfDay(sumDuration.getSeconds());
    }
}





