package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import lombok.Value;
@Value
public class PayrollUnitPriceHistoryCommand {
    
    /**
    * 会社ID
    */
    private String cId;

    /**
     * コード
     */
    private String code;

    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 開始年月
    */
    private Integer startYearMonth;
    
    /**
    * 終了年月
    */
    private Integer endYearMonth;

    /**
     * Mode
     */
    private int isMode;


}
