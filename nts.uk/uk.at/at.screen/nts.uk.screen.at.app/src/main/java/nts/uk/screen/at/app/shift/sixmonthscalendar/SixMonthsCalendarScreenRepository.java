package nts.uk.screen.at.app.shift.sixmonthscalendar;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarScreenDto;

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
    List<SixMonthsCalendarScreenDto> getSixCalendarCompanyByYearMonth(String companyId, Period yearMonth);
}
