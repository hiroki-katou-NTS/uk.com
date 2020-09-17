package nts.uk.screen.at.app.shift.businesscalendar.day;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarClassScreenDto;

import java.util.List;

public interface ClassificationCalendarRepository {
    /**
     * Get Company Non-Working Days
     * @param conpanyId , classId, yearMonth
     *        職場ID, 期間
     * @return List<SixMonthsCalendarClassScreenDto>
     *     	List<分類営業日カレンダー日次>
     */
    List<SixMonthsCalendarClassScreenDto> getNonWorkingDaysClassification(String conpanyId, String classId, DatePeriod yearMonth);
}
