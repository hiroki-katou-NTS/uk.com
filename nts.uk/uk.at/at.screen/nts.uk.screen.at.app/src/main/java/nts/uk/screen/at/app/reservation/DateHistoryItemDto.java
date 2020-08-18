package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateHistoryItemDto {
    public String historyId;
    public String startDate;
    public String endDate;

}
