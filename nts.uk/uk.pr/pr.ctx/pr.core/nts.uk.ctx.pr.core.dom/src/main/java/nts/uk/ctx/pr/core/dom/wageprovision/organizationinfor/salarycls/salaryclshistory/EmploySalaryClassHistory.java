package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 社員給与分類項目
*/
@Getter
public class EmploySalaryClassHistory extends AggregateRoot implements ContinuousResidentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {

    /**
     * 社員ID
     */
    public String sid;

    /**
    * 年月履歴
    */
    private List<YearMonthHistoryItem> history;
    
    public EmploySalaryClassHistory(String sid, List<YearMonthHistoryItem> history) {
        this.sid = sid;
        this.history = history;
    }


    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }
    @Override
    public void exCorrectToRemove(YearMonthHistoryItem latest) {
        latest.changeSpan(latest.span().newSpanWithMaxEnd());
    }
    public EmploySalaryClassHistory toDomain() {
        return new EmploySalaryClassHistory(this.sid,this.history);


    }
}
