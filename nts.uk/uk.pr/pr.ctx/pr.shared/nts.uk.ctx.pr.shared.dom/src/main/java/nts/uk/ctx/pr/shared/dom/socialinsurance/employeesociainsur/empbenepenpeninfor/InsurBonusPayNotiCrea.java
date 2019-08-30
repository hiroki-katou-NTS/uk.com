package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 厚生年金保険賞与支払届作成時情報
*/
@Getter
public class InsurBonusPayNotiCrea extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 二以上勤務
    */
    private int twoOrMore;
    
    public InsurBonusPayNotiCrea(String employeeId,int twoOrMore ) {
        this.employeeId = employeeId;
        this.twoOrMore =twoOrMore ;
    }
    
}
