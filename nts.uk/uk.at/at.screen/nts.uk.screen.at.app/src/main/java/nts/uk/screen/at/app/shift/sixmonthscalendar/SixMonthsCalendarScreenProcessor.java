package nts.uk.screen.at.app.shift.sixmonthscalendar;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarScreenDto;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SixMonthsCalendarScreenProcessor {
    @Inject
    private SixMonthsCalendarScreenRepository repository;
    public List<SixMonthsCalendarScreenDto> getSixMonthsCalendar(String companyId, GeneralDate startDate, GeneralDate endDate) {
        Period period = new Period(startDate, endDate);
//        String companyId = AppContexts.user().companyId();
        return repository.getSixCalendarCompanyByYearMonth(companyId, period);
    }
}
