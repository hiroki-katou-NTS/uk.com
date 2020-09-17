package nts.uk.screen.at.app.shift.businesscalendar.day.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SixMonthsCalendarWorkPlaceScreenDto {
    /** work place id
     **/
    private String workPlaceId;

    /** date
     **/
    private GeneralDate date;

    /** working day
     **/
    private int workingDayAtr;
}
