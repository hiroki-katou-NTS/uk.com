package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPrice;

/**
* 給与会社単価: DTO
*/
@AllArgsConstructor
@Value
public class PayrollUnitPriceDto
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
    
    
    public static PayrollUnitPriceDto fromDomain(PayrollUnitPrice domain)
    {
        return new PayrollUnitPriceDto(domain.getCode().v(), domain.getCId(), domain.getName().v());
    }
    
}
