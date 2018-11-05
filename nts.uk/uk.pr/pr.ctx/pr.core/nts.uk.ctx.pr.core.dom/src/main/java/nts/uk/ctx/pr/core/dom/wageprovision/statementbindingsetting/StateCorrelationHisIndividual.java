package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;

/**
* 明細書紐付け履歴（個人）
*/
@Getter
public class StateCorrelationHisIndividual extends AggregateRoot implements UnduplicatableHistory {
    
    /**
    * 社員ID
    */
    private String empID;
    
    /**
    * 履歴
    */
    private List<YearMonthHistoryItem> history;
    
    public StateCorrelationHisIndividual(String empId, List<YearMonthHistoryItem> history) {
        this.empID = empId;
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return history;
    }

    
}
