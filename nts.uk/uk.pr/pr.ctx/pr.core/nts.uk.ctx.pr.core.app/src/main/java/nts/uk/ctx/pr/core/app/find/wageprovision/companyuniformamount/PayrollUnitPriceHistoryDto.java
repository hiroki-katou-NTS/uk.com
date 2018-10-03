package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;

/**
* 給与会社単価履歴: DTO
*/
@AllArgsConstructor
@Value
public class PayrollUnitPriceHistoryDto
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
    
    
    public static PayrollUnitPriceHistoryDto fromDomain(PayrollUnitPriceHistory domain)
    {
        return new PayrollUnitPriceHistoryDto(domain.getCId(), domain.getHistory().identifier(), domain.getCode().v(), domain.getHistory().start().v(), domain.getHistory().end().v());
    }
    
}
