package com.toyproject.stopwatch.domain.watch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;

class TimeMeasurementTest {


    @Test
    void constructTest(){
        final TimeMeasurement timeMeasurement = new TimeMeasurement(LocalDateTime.now(), MeasuringStatus.STUDY);
    }

    @Test
    @DisplayName("시작 시간과 시작 이후의 시간(현재시간)의 차이를 구할 수 있다.")
    void calculateTerm(){

        // given
        final LocalDateTime startTime = LocalDateTime.of(2023, 11, 6, 20, 9, 50);
        final LocalDateTime targetTime = LocalDateTime.of(2023, 11, 6, 23, 8, 45);
        final TimeMeasurement timeMeasurement = new TimeMeasurement(startTime, MeasuringStatus.STUDY);

        // when
        LocalTime localTime = timeMeasurement.compareTime(targetTime);

        // then
        Assertions.assertThat(localTime).isEqualTo(LocalTime.of(2, 58, 55));
    }

    @Test
    @DisplayName("측정을 종료하면 시작 시간과 종료 시간의 시간 차이를 구할 수 있다.")
    void getTermBetweenStartAndEnd(){
        // given
        final LocalDateTime startTime = LocalDateTime.of(2023, 11, 6, 20, 9, 50);
        final LocalDateTime endTime = LocalDateTime.of(2023, 11, 6, 23, 8, 45);
        final TimeMeasurement timeMeasurement = new TimeMeasurement(startTime, MeasuringStatus.STUDY);

        // when
        timeMeasurement.endMeasuring(endTime);
        LocalTime totalTime = timeMeasurement.getTotalTime();

        // then
        Assertions.assertThat(totalTime).isEqualTo(LocalTime.of(2, 58, 55));
    }

}



