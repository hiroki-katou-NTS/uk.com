package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.history.DateHistoryItem;

/**
* 社員厚生年金保険得喪期間項目
*/
@Getter
public class EmployWelPenInsurAche extends DomainObject
{
    
    /**
    * 厚年得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 期間
    */
    private DateHistoryItem datePeriod;
    
    public EmployWelPenInsurAche(String historyId,DateHistoryItem datePeriod) {
        this.historyId = historyId;
        this.datePeriod = datePeriod;
    }
    
}
