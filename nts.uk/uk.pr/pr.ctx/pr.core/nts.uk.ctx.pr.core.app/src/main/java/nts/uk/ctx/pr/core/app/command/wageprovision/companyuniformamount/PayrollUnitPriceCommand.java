package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import lombok.Value;

@Value
public class PayrollUnitPriceCommand
{
    /**
     * 会社ID
     */
    private String cId;

    /**
    * コード
    */
    private String code;

    /**
    * 名称
    */
    private String name;
    

}
