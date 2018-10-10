package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 年月期間の汎用履歴項目
 */
@Getter
public class GenericHistYMPeriod extends DomainObject {

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 期間
     */
    private YearMonthPeriod periodYearMonth;

    public GenericHistYMPeriod(String histId, int startDate, int endDate) {
        this.historyID = histId;
        this.periodYearMonth = new YearMonthPeriod(new YearMonth(startDate), new YearMonth(endDate));
    }

}
