package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

import java.util.List;

/**
* 社員社保事業所所属履歴
*/
@Getter
public class EmpCorpHealthOffHis extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 期間
    */
    private List<DateHistoryItem> period;
    
    public EmpCorpHealthOffHis(String employeeId,List<DateHistoryItem> period) {
        this.employeeId = employeeId;
        this.period = period;
    }
    
}
