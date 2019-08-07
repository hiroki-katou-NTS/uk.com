package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

/**
* 健保組合加入期間情報
*/
@Getter
public class HealInsurPortPerIntell extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 期間
    */
    private DateHistoryItem datePeriod;
    
    public HealInsurPortPerIntell(String employeeId,  DateHistoryItem datePeriod) {
        this.employeeId = employeeId;
        this.datePeriod = datePeriod ;
    }
    
}
