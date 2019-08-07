package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

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
