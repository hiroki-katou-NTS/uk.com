package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.Value;

@Value
public class HealthCarePortInforCommand
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 健康保険組合番号
    */
    private int healInsurUnionNumber;
    

}
