package nts.uk.screen.at.app.shift.sixmonthscalendar;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarClassScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarCompanyScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarWorkPlaceScreenDto;

import java.util.List;

/**
 *
 * @author datnk
 *
 */
public interface SixMonthsCalendarScreenRepository {
    /**
     *
     * @param params
     * @return
     */
    List<SixMonthsCalendarCompanyScreenDto> getSixMonthsCalendarCompanyByYearMonth(String companyId, DatePeriod yearMonth);

    List<SixMonthsCalendarWorkPlaceScreenDto> getSixMonthsCalendarWorkPlaceByYearMonth(String workPlaceId, DatePeriod yearMonth);

    List<SixMonthsCalendarClassScreenDto> getSixMonthsCalendarClassByYearMonth(String classId, DatePeriod yearMonth);
}
