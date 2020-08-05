package nts.uk.screen.at.app.shift.sixmonthscalendar;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarClassScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarCompanyScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarWorkPlaceScreenDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SixMonthsCalendarScreenProcessor {
    @Inject
    private SixMonthsCalendarScreenRepository repository;

    public List<SixMonthsCalendarCompanyScreenDto> getSixMonthsCalendarCompany(DatePeriod datePeriod) {
        String companyId = AppContexts.user().companyId();
        return repository.getSixMonthsCalendarCompanyByYearMonth(companyId, datePeriod);
    }

    public List<SixMonthsCalendarWorkPlaceScreenDto> getSixMonthsCalendarWorkPlace(String workPlaceId, DatePeriod datePeriod) {
//        String companyId = AppContexts.user().companyId();
        return repository.getSixMonthsCalendarWorkPlaceByYearMonth(workPlaceId, datePeriod);
    }

    public List<SixMonthsCalendarClassScreenDto> getSixMonthsCalendarClass(String classId, DatePeriod datePeriod) {
//        String companyId = AppContexts.user().companyId();
        return repository.getSixMonthsCalendarClassByYearMonth(classId, datePeriod);
    }
}
