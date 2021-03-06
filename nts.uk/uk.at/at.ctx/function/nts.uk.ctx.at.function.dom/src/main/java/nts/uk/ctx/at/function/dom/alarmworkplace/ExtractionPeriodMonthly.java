package nts.uk.ctx.at.function.dom.alarmworkplace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarmworkplace.month.EndMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.month.StartMonth;

@Getter
@NoArgsConstructor
public class ExtractionPeriodMonthly implements RangeToExtract {

    /**開始日*/
    private StartMonth startMonth;

    /**終了日*/
    private EndMonth endMonth;

    public ExtractionPeriodMonthly(StartMonth startMonth, EndMonth endMonth) {
        super();
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }
}
