package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
* 社員雇用保険事業所履歴
*/
@Getter
public class EmpEstabInsHist extends AggregateRoot implements ContinuousResidentHistory<DateHistoryItem,DatePeriod, GeneralDate> {
    
    /**
    * 社員ID
    */
    private String sid;
    
    /**
    * 期間
    */
    private List<DateHistoryItem> dateHistoryItemList;

    @Override
    public List<DateHistoryItem> items() {
        return dateHistoryItemList;
    }

    public EmpEstabInsHist(String sid, List<DateHistoryItem> dateHistoryItemList) {
        this.sid = sid;
        this.dateHistoryItemList = dateHistoryItemList;
    }
}
