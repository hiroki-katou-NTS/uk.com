package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import java.util.List;

/**
* 給与汎用パラメータ年月日履歴
*/
@Getter
public class SalGenParaDateHistory extends AggregateRoot  implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
    
    /**
    * パラメータNo
    */
    private String paraNo;
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * 履歴
    */
    private List<DateHistoryItem> dateHistoryItem;
    
    public SalGenParaDateHistory(String paraNo, String cid, List<DateHistoryItem> dateHistoryItem) {
        this.paraNo =paraNo ;
        this.cId = cid;
        this.dateHistoryItem = dateHistoryItem;
    }

    @Override
    public List<DateHistoryItem> items() {
        return this.dateHistoryItem;
    }
}
