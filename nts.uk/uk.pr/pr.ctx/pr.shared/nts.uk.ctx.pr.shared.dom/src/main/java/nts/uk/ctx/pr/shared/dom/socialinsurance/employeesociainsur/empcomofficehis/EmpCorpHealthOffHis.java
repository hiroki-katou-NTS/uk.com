package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
* 社員社保事業所所属履歴
*/
@Getter
public class EmpCorpHealthOffHis extends AggregateRoot
        implements ContinuousHistory<DateHistoryItem, DatePeriod, GeneralDate> {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 期間
    */
    private List<DateHistoryItem> period;
    
    public EmpCorpHealthOffHis(String employeeId,List<DateHistoryItem> period) {
        this.employeeId = employeeId;
        this.period = period;
    }


    @Override
    public List<DateHistoryItem> items() {
        return period;
    }

    @Override
    public void exCorrectToRemove(DateHistoryItem itemToBeRemoved) {
        this.latestStartItem().ifPresent(latest -> {
            latest.changeSpan(latest.span().newSpanWithMaxEnd());
        });
    }
}
