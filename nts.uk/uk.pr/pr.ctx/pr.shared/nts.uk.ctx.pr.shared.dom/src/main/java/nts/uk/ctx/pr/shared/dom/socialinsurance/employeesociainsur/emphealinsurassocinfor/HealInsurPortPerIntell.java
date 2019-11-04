package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;

import java.util.List;

/**
* 健保組合加入期間情報
*/
@Getter
public class HealInsurPortPerIntell extends AggregateRoot implements ContinuousResidentHistory {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 期間
    */
    private List<DateHistoryItem> datePeriod;
    
    public HealInsurPortPerIntell(String employeeId,  List<DateHistoryItem> datePeriod) {
        this.employeeId = employeeId;
        this.datePeriod = datePeriod ;
    }

    @Override
    public List items() {
        return datePeriod;
    }
}
