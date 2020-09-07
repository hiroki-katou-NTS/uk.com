package nts.uk.screen.at.app.shift.businesscalendar.day.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SixMonthsCalendarCompanyScreenDto {
    /** company id
     **/
    private String companyId;

    /** date
     **/
    private GeneralDate date;

    /** working day
     **/
    private int workingDayAtr;

}
