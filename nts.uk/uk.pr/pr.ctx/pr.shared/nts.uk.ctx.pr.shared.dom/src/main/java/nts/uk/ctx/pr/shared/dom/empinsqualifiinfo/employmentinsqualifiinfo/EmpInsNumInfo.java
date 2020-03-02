package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 雇用保険番号情報
*/
@Getter
public class EmpInsNumInfo extends AggregateRoot {
    
    /**
    * 雇用保険履歴ID
    */
    private String histId;
    
    /**
    * 雇用保険番号
    */
    private EmpInsNumber empInsNumber;
    
    public EmpInsNumInfo(String histId, String empInsNumber) {
        this.histId = histId;
        this.empInsNumber = new EmpInsNumber(empInsNumber);
    }
    
}
