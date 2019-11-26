package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;

@Value
public class EmpBasicPenNumInforCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 基礎年金番号
    */
    private String basicPenNumber;
    

}
