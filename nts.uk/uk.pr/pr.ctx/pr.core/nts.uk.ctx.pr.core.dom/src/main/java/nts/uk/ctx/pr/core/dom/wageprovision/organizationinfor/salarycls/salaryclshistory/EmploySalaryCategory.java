package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員給与分類項目
*/
@Getter
public class EmploySalaryCategory extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    public String hisId;
    
    /**
    * 給与分類コード
    */
    String salaryClassCode;
    
    public EmploySalaryCategory(String hisId, String salaryClassCode) {
        this.hisId = hisId ;
        this.salaryClassCode = salaryClassCode;
    }
    
}
