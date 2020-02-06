package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.ArrayList;
import java.util.List;

/**
 * 社員住民税納付先情報
 */
@AllArgsConstructor
public class EmployeeResidentTaxPayeeInfo extends AggregateRoot
        implements UnduplicatableHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth>{

    /**
     * 社員ID
     */
    @Getter
    private String sid;

    /**
     * 期間
     */
    private List<YearMonthHistoryItem> historyItems;
    @Override
    public List<YearMonthHistoryItem> items() {
        return this.historyItems;
    }

}
