package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;

import java.util.List;

/**
* 社員厚生年金保険資格情報: DTO
*/
@AllArgsConstructor
@Value
public class EmpWelfarePenInsQualiInforDto
{
    
    /**
    * 社員ID
    */
    private String employeeId;

    private List<EmployWelPenInsurAche> mournPeriod;
    
    
    public static EmpWelfarePenInsQualiInforDto fromDomain(EmpWelfarePenInsQualiInfor domain)
    {
        return new EmpWelfarePenInsQualiInforDto(domain.getEmployeeId(), domain.getMournPeriod());
    }
    
}
