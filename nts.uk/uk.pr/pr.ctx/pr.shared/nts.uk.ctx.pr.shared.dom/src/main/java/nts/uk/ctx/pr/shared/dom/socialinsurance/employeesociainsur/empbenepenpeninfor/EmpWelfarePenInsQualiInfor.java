package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
* 社員厚生年金保険資格情報
*/
@Getter
public class EmpWelfarePenInsQualiInfor extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 得喪期間
    */
    private List<EmployWelPenInsurAche> mournPeriod;

    public EmpWelfarePenInsQualiInfor(String employeeId, List<EmployWelPenInsurAche> mournPeriod) {
        this.employeeId = employeeId;
        this.mournPeriod = mournPeriod;
    }
    
}
