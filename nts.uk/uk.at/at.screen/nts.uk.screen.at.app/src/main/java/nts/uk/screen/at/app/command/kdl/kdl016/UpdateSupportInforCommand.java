package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;

@Data
public class UpdateSupportInforCommand {

    private String employeeId;

    /** 応援種類 */
    private int supportType;

    /** 応援期間 */
    private String periodStart;
    private String periodEnd;

    /** 応援時間帯 */
     private TimeSpanForCalcDto supportTimeSpan;

    public DatePeriod toDatePeriod() {
        return new DatePeriod(GeneralDate.fromString(this.periodStart, "yyyy/MM/dd"), GeneralDate.fromString(this.periodEnd, "yyyy/MM/dd"));
    }
}
