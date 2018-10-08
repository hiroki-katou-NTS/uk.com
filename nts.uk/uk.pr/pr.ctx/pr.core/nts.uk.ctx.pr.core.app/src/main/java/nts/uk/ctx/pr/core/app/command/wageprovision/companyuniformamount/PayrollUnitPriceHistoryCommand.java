package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import lombok.Value;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class PayrollUnitPriceHistoryCommand
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
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 開始年月
    */
    private int startYearMonth;
    
    /**
    * 終了年月
    */
    private int endYearMonth;

    /**
     * Mode
     */
    private int isMode;


}
