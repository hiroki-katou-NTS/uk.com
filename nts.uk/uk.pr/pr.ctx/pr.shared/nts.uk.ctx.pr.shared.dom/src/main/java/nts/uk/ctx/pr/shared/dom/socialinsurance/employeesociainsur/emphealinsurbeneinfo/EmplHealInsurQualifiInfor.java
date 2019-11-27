package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
* 社員健康保険資格情報
*/
@Getter
public class EmplHealInsurQualifiInfor extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 得喪期間
    */
    private List<EmpHealthInsurBenefits> mourPeriod;
    
    public EmplHealInsurQualifiInfor(String employeeId,List<EmpHealthInsurBenefits> mourPeriod) {
        this.employeeId = employeeId;
        this.mourPeriod = mourPeriod;
    }
    
}
