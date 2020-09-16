package nts.uk.screen.at.app.shift.businesscalendar.day;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarWorkPlaceScreenDto;

import java.util.List;

public interface WorkdayCalendarRepository {
    /**
     * Get Company Non-Working Days
     * @param workPlaceId , yearMonth
     *        職場ID, 期間
     * @return List<SixMonthsCalendarWorkPlaceScreenDto>
     *     	List<職場営業日カレンダー日次>
     */
    List<SixMonthsCalendarWorkPlaceScreenDto> getNonWorkingDaysWorkplace(String workPlaceId, DatePeriod yearMonth);
}
