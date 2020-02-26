package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;

/**
* 社保勤務形態履歴
*/
@Getter
@AllArgsConstructor
public class CorEmpWorkHis extends AggregateRoot implements ContinuousResidentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {
    
    /**
    * 社員ID
    */
    private String empId;
    
    /**
    * 期間
    */
    private List<YearMonthHistoryItem> history;

    @Override
    public List<YearMonthHistoryItem> items() {
        return history;
    }

    @Override
    public void exCorrectToRemove(YearMonthHistoryItem latest) {
        latest.changeSpan(latest.span().newSpanWithMaxEnd());
    }
}
