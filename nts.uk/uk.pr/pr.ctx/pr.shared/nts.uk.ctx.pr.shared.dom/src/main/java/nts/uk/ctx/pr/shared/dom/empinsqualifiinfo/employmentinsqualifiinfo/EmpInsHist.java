package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

/**
* 社員雇用保険履歴
*/
@Getter
public class EmpInsHist extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String sid;
    
    /**
    * 期間
    */
    private List<DateHistoryItem> historyItem;
    
    public EmpInsHist(String cid,List<DateHistoryItem> historyItem) {
        this.sid = sid;
        this.historyItem = historyItem;
    }
    
}
