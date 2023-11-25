package com.toyproject.stopwatch.domain.watch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

class TimeMeasurementGroupTest {

    @Test
    @DisplayName("어러 개의 timeMeasurement 를 담을 수 있다.")
    void canContainManyTimeMeasurement(){

        // given
        final LocalDateTime startTime = LocalDateTime.of(2023, 11, 18, 12, 0, 0);
        final LocalDateTime firstChangeTime = LocalDateTime.of(2023, 11, 18, 13, 0, 0);
        final LocalDateTime secondChangeTime = LocalDateTime.of(2023, 11, 18, 14, 0, 0);
        final TimeMeasurement timeMeasurement = new TimeMeasurement(startTime, MeasuringStatus.STUDY);
        final TimeMeasurementGroup timeMeasurementGroup = new TimeMeasurementGroup(timeMeasurement);

        // when
        timeMeasurementGroup.changeStatus(firstChangeTime);
        timeMeasurementGroup.changeStatus(secondChangeTime);


        // then
        Assertions.assertThat(timeMeasurementGroup.recordsCount()).isEqualTo(3);
    }


    @Test
    @DisplayName("한 세트에서의 전체 공부 시간을 구할 수 있다.")
    void canGetTotalStudyTime(){

        // given
        final LocalDateTime startTime = LocalDateTime.of(2023, 11, 18, 12, 0, 0);
        final LocalDateTime firstChangeTime = LocalDateTime.of(2023, 11, 18, 13, 5, 30);
        final LocalDateTime secondChangeTime = LocalDateTime.of(2023, 11, 18, 13, 15, 0);
        final LocalDateTime thirdChangeTime = LocalDateTime.of(2023, 11, 18, 15, 0, 45);
        final LocalDateTime ForthChangeTime = LocalDateTime.of(2023, 11, 18, 15, 5, 0);
        final LocalDateTime fifthChangeTime = LocalDateTime.of(2023, 11, 18, 17, 0, 0);

        final TimeMeasurement timeMeasurement = new TimeMeasurement(startTime, MeasuringStatus.STUDY);
        final TimeMeasurementGroup timeMeasurementGroup = new TimeMeasurementGroup(timeMeasurement);
        timeMeasurementGroup.changeStatus(firstChangeTime);
        timeMeasurementGroup.changeStatus(secondChangeTime);
        timeMeasurementGroup.changeStatus(thirdChangeTime);
        timeMeasurementGroup.changeStatus(ForthChangeTime);
        timeMeasurementGroup.changeStatus(fifthChangeTime);

        // when
        final LocalDateTime checkTime = LocalDateTime.of(2023, 11, 18, 17, 30, 30);
        final Duration totalStudyTime = timeMeasurementGroup.getTotalStudyTime(checkTime);

        // then
        Assertions.assertThat(totalStudyTime).isEqualTo(Duration.ofHours(4).plusMinutes(46).plusSeconds(15));
    }




}













