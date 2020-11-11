package nts.uk.ctx.at.function.dom.alarmworkplace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyEndMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyStartMonth;

@Getter
@NoArgsConstructor
public class ExtractionPeriodMonthly implements RangeToExtract {

    /** 終了月の指定方法 */
    private SpecifyEndMonth specifyEndMonth;

    /** 終了月の指定方法 */
    private SpecifyStartMonth specifyStartMonth;

    private ExtractionEndMonth endMonth;

    private ExtractionStartMonth startMonth;


    public ExtractionPeriodMonthly(ExtractionEndMonth endMonth, ExtractionStartMonth startMonth) {
        super();
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }
}
