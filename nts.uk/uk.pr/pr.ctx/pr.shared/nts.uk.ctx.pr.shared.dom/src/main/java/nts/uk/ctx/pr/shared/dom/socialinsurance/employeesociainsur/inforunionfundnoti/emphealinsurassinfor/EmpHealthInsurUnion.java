package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* emphealinsurassinfor
*/
@Getter
public class EmpHealthInsurUnion extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 健保固有項目
    */
    private Optional<HealInsurItems> healthInsurInherentProject;
    
    public EmpHealthInsurUnion(String employeeId, String healInsurInherenPr) {
        this.employeeId = employeeId;
        this.healthInsurInherentProject = healInsurInherenPr == null ? Optional.empty() : Optional.of(new HealInsurItems(healInsurInherenPr));
    }
    
}
