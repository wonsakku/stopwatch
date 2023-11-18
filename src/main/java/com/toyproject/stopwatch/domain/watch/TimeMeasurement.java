package com.toyproject.stopwatch.domain.watch;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
public class TimeMeasurement {

    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private MeasuringStatus measuringStatus;


    public TimeMeasurement(LocalDateTime startTime, MeasuringStatus measuringStatus) {
        this.startTime = startTime;
        this.measuringStatus = measuringStatus;
    }


    public LocalTime compareTime(LocalDateTime targetTime) {
        final long secondsTerm = ChronoUnit.SECONDS.between(this.startTime, targetTime);
        return LocalTime.ofSecondOfDay(secondsTerm);
    }

    public void endMeasuring(LocalDateTime endTime){
        this.endTime = endTime;
    }


    public LocalTime getTotalTime() {
        if(this.endTime == null){
            throw new IllegalStateException("시간 측정이 종료되지 않았습니다.");
        }
        return compareTime(this.endTime);
    }

    public MeasuringStatus nextMeasuringStatus() {

        if(this.measuringStatus == MeasuringStatus.STUDY){
            return MeasuringStatus.BREAK;
        }

        if(this.measuringStatus == MeasuringStatus.BREAK){
            return MeasuringStatus.STUDY;
        }

        throw new RuntimeException();
    }

    public boolean isMeasuringEnd() {
        return this.endTime != null;
    }
}
