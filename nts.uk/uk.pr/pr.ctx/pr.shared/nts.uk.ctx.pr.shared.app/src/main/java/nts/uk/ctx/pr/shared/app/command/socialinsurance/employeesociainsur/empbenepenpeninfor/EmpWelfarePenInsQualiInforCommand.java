package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;

import java.util.List;

@Value
public class EmpWelfarePenInsQualiInforCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    private List<EmployWelPenInsurAche> predior;
    

}
