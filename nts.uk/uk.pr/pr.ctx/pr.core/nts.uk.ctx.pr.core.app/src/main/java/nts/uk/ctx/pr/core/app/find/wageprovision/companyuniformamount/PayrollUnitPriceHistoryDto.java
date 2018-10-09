package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistory;

import java.util.List;
import java.util.stream.Collectors;

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


    public static List<PayrollUnitPriceHistoryDto> fromDomain(PayrollUnitPriceHistory domain)
    {
        List<PayrollUnitPriceHistoryDto> payrollUnitPriceHistoryDto = domain.getHistory().stream().map(item -> {
            return new PayrollUnitPriceHistoryDto(domain.getCId(),item.identifier(),domain.getCode().v(), Integer.parseInt(item.start().toString()), Integer.parseInt(item.end().toString()));
        }).collect(Collectors.toList());
        return payrollUnitPriceHistoryDto;
    }
    
}
