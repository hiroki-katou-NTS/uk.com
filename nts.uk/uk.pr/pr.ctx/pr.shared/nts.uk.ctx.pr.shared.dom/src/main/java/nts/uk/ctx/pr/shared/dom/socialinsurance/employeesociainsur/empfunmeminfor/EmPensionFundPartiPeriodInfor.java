package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

/**
* 厚生年金基金加入期間情報
*/
@Getter
public class EmPensionFundPartiPeriodInfor extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 期間
    */
    private DateHistoryItem datePeriod;
    
    public EmPensionFundPartiPeriodInfor(String employeeId, DateHistoryItem datePeriod) {
        this.employeeId = employeeId;
        this.datePeriod = datePeriod;
    }
    
}
