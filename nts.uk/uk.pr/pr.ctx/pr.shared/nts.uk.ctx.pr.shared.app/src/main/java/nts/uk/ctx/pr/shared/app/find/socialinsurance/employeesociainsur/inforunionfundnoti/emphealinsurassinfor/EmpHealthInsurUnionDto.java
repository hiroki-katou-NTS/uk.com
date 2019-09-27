package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnion;

/**
* emphealinsurassinfor: DTO
*/
@AllArgsConstructor
@Value
public class EmpHealthInsurUnionDto
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 健保固有項目
    */
    private String healthInsurInherentProject;
    
    
    public static EmpHealthInsurUnionDto fromDomain(EmpHealthInsurUnion domain)
    {
        return new EmpHealthInsurUnionDto(domain.getEmployeeId(), domain.getHealthInsurInherentProject().map(i->i.v()).orElse(null));
    }
    
}
