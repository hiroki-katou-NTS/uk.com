package nts.uk.ctx.pr.core.dom.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 雇用保険履歴
*/
@AllArgsConstructor
@Getter
public class EmpInsurHis extends AggregateRoot implements ContinuousResidentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth>
{
    
    /**
    * 会社ID
    */
    private String cid;
    
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
