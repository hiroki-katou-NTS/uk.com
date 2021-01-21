package nts.uk.screen.at.app.shift.businesscalendar.day.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SixMonthsCalendarClassScreenDto {
    /** company id
    **/
    private String companyId;

    /** class id**/
    private String classId;

    /** date**/
    private GeneralDate date;

    /** working day**/
    private int workingDayAtr;

}
