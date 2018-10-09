package nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.CompanyUnitPriceCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 給与会社単価履歴
 */

@AllArgsConstructor
@Getter
public class PayrollUnitPriceHistory extends AggregateRoot implements ContinuousResidentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {
    /**
     * コード
     */
    private CompanyUnitPriceCode code;

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;


    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }

    public void exCorrectToRemove(YearMonthHistoryItem latest) {
        latest.changeSpan(latest.span().newSpanWithMaxEnd());
    }
}
