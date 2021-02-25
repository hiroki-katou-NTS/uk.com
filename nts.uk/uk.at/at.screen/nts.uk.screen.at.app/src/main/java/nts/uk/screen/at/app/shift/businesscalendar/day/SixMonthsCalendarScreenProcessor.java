package nts.uk.screen.at.app.shift.businesscalendar.day;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarClassScreenDto;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarCompanyScreenDto;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarWorkPlaceScreenDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SixMonthsCalendarScreenProcessor {
    @Inject
    private ClassificationCalendarRepository classificationCalendarRepository;

    @Inject
    private CompanyCalendarRepository companyCalendarRepository;

    @Inject
    private WorkdayCalendarRepository workdayCalendarRepositoryRepository;

    public List<SixMonthsCalendarCompanyScreenDto> getSixMonthsCalendarCompany(DatePeriod datePeriod) {
        String companyId = AppContexts.user().companyId();
        return companyCalendarRepository.getCompanyNonWorkingDays(companyId, datePeriod);
    }

    public List<SixMonthsCalendarWorkPlaceScreenDto> getSixMonthsCalendarWorkPlace(String workPlaceId, DatePeriod datePeriod) {
        return workdayCalendarRepositoryRepository.getNonWorkingDaysWorkplace(workPlaceId, datePeriod);
    }

    public List<SixMonthsCalendarClassScreenDto> getSixMonthsCalendarClass(String classId, DatePeriod datePeriod) {
        String companyId = AppContexts.user().companyId();
        return classificationCalendarRepository.getNonWorkingDaysClassification(companyId, classId, datePeriod);
    }
}
