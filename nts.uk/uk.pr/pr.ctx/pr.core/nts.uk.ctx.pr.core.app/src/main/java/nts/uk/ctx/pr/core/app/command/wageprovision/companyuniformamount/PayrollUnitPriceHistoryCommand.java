package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import lombok.Value;

@Value
public class PayrollUnitPriceHistoryCommand
{
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * コード
    */
    private String code;
    
    /**
    * 開始年月
    */
    private int startYearMonth;
    
    /**
    * 終了年月
    */
    private int endYearMonth;

    private int isMode;
    

}
