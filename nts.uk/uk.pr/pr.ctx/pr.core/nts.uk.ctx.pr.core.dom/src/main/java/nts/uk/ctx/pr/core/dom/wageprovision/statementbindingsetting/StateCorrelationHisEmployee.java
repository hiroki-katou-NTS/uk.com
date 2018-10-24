package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 明細書紐付け履歴（雇用）
*/
@Getter
public class StateCorrelationHisEmployee extends AggregateRoot implements ContinuousResidentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * 履歴
    */
    private List<YearMonthHistoryItem> history;

    public StateCorrelationHisEmployee(String cid, List<YearMonthHistoryItem> history) {
        this.cId = cid;
        this.history = history;
    }

    @Override
    public List items() {
        return history;
    }
    
}
