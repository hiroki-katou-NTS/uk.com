package nts.uk.ctx.at.function.dom.alarmworkplace;

import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;

public class ExtractionPeriod implements RangeToExtract,EndDate,StartDate {

    private EndDate endDate;

    private StartDate startDate;

    @Override
    public EndSpecify getEndSpecify() {
        return null;
    }

    @Override
    public StartSpecify getStartSpecify() {
        return null;
    }
}
