package nts.uk.file.at.infra.schedule.personalschedulebydate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
class TimeRangeLimitDto {
    private Integer minLimit;
    private Integer maxLimit;
}

@AllArgsConstructor
@Getter
class DoubleWorkTimeCheckedDto {
    private TimeCheckedDto time1;
    private TimeCheckedDto time2;
}

@AllArgsConstructor
@Getter
class TimeCheckedDto {
    private Integer startTime;
    private Integer endTime;
}

enum OutputType {
    TOTAL_MINUTE,
    HOUR,
    COLUMN
}
