package nts.uk.screen.at.app.shift.sixmonthscalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SixMonthsCalendarCompanyScreenDto {
    private String companyId;

    private GeneralDate date;

    private int workingDayAtr;

    public static SixMonthsCalendarCompanyScreenDto fromDomain(CalendarCompany domain){
        return new SixMonthsCalendarCompanyScreenDto(
                domain.getCompanyId(),
                domain.getDate(),
                domain.getWorkingDayAtr().value);
    }
}
