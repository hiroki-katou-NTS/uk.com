package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import lombok.Value;

@Value
public class PayrollUnitPriceCommand
{
    
    /**
    * コード
    */
    private String code;
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * 名称
    */
    private String name;
    

}
