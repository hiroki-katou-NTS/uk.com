package nts.uk.ctx.at.function.dom.alarmworkplace;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;

@Getter
public class ExtractionPeriodDaily implements RangeToExtract {

    /**終了日*/
    private StartDate startDate;

    /**開始日*/
    private EndDate endDate;

    public ExtractionPeriodDaily(StartDate startDate, EndDate endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
