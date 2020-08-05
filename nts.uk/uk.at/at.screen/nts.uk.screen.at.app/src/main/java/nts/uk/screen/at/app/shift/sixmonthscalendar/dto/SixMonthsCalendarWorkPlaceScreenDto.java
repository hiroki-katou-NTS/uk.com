package nts.uk.screen.at.app.shift.sixmonthscalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SixMonthsCalendarWorkPlaceScreenDto {
    private String workPlaceId;

    private GeneralDate date;

    private int workingDayAtr;
}
