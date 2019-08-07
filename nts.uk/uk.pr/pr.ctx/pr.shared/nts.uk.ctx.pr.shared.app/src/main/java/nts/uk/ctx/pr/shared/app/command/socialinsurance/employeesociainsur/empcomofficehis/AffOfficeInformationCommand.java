package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Value;

@Value
public class AffOfficeInformationCommand
{
    
    /**
    * 社会保険事業所コード
    */
    private String socialInsuranceCode;
    
    /**
    * 履歴ID
    */
    private String historyID;
    

}
