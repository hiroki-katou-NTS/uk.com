package nts.uk.screen.at.app.shift.businesscalendar.day;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarCompanyScreenDto;

import java.util.List;

public interface CompanyCalendarRepository {

    /**
     * Get Company Non-Working Days
     * @param companyId , yearMonth
     *        会社ID, 期間
     * @return List<SixMonthsCalendarCompanyScreenDto>
     */
    List<SixMonthsCalendarCompanyScreenDto> getCompanyNonWorkingDays(String companyId, DatePeriod yearMonth);
}
