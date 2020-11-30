package nts.uk.ctx.bs.employee.pub.temporaryabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateHistoryItemExport {

    public String historyId;
    public GeneralDate startDate;
    public GeneralDate endDate;

}
