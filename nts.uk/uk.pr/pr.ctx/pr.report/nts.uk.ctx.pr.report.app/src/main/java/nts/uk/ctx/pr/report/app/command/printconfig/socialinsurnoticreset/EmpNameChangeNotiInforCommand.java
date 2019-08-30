package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import lombok.Value;

@Value
public class EmpNameChangeNotiInforCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * 健康保険被保険者証不要
    */
    private int healInsurPersonNoNeed;
    
    /**
    * その他
    */
    private int other;
    
    /**
    * その他備考
    */
    private String otherRemarks;
    

}
