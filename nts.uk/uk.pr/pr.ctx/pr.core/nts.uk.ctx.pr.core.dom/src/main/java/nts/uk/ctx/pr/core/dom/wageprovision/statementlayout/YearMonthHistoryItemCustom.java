package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Getter
@Setter
public class YearMonthHistoryItemCustom extends YearMonthHistoryItem {
    int layoutPattern;

    public YearMonthHistoryItemCustom(String historyId, YearMonthPeriod period, int layoutPattern) {
        super(historyId, period);
        this.layoutPattern = layoutPattern;
    }
}
