package com.toyproject.stopwatch.domain.watch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.*;

import static org.assertj.core.api.Assertions.*;

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
        Duration localTime = timeMeasurement.compareTime(targetTime);

        // then
        assertThat(localTime).isEqualTo(Duration.ofHours(2).plusMinutes(58).plusSeconds(55));
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
        Duration totalTime = timeMeasurement.getTotalTime();

        // then
        assertThat(totalTime).isEqualTo(Duration.ofHours(2).plusMinutes(58).plusSeconds(55));
    }


    @DisplayName("TimeMeasurement 에서 현재 상태와 반대되는 상태를 구할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"STUDY,BREAK", "BREAK,STUDY"}, delimiter = ',')
    void getNextMeasurementStatusTest(String currentStatus, String nextStatus){

        // given
        final MeasuringStatus current = MeasuringStatus.valueOf(currentStatus);
        final TimeMeasurement timeMeasurement = new TimeMeasurement(LocalDateTime.now(), current);
        final MeasuringStatus next = MeasuringStatus.valueOf(nextStatus);


        // when, then
        assertThat(timeMeasurement.nextMeasuringStatus()).isEqualTo(next);
    }

    @DisplayName("현재 측정이 완료되었는지 확인할 수 있다.")
    @Test
    void endMeasuringTest(){

        final TimeMeasurement timeMeasurement = new TimeMeasurement(LocalDateTime.now(), MeasuringStatus.STUDY);

        assertThat(timeMeasurement.isMeasuringEnd()).isFalse();
        timeMeasurement.endMeasuring(LocalDateTime.now());
        assertThat(timeMeasurement.isMeasuringEnd()).isTrue();
    }



}





