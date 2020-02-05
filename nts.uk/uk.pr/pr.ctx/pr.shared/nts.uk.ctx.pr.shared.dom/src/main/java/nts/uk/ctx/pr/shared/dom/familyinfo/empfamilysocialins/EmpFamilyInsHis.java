package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
* 社員家族社会保険履歴
*/
@Getter
public class EmpFamilyInsHis extends AggregateRoot implements ContinuousHistory<DateHistoryItem, DatePeriod, GeneralDate> {
    
    /**
    * 社員ID
    */
    private String empId;
    
    /**
    * 家族ID
    */
    private int familyId;
    
    /**
    * 履歴
    */
    private List<DateHistoryItem> dateHistoryItem;
    
    public EmpFamilyInsHis(String empId, int familyId , List<DateHistoryItem> dateHistoryItem) {
        this.empId = empId;
        this.familyId = familyId;
        this.dateHistoryItem = dateHistoryItem;
    }

    @Override
    public List<DateHistoryItem> items() {
        return this.dateHistoryItem;
    }
}
