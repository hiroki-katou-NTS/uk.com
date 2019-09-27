package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;


import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class EmpHealthInsurUnionCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 健保固有項目
    */
    private String healthInsurInherentProject;
    

}
