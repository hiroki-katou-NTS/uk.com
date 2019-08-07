package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Value;

@Value
public class WelfarePenTypeInforCommand
{
    
    /**
    * 厚年得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 坑内員区分
    */
    private int undergroundDivision;
    

}
