package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;

@Value
public class MultiEmpWorkInfoCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 二以上事業所勤務者
    */
    private int isMoreEmp;
    

}
