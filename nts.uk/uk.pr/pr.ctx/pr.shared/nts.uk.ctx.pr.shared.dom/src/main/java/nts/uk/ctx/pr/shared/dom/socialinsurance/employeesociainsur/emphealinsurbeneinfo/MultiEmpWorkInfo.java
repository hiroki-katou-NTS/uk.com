package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員二以上事業所勤務情報
*/
@Getter
public class MultiEmpWorkInfo extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String empId;
    
    /**
    * 二以上事業所勤務者
    */
    private int isMoreEmp;
    
    public MultiEmpWorkInfo(String employeeId, int isMoreEmp) {
        this.empId = employeeId;
        this.isMoreEmp = isMoreEmp;
    }
    
}
