package nts.uk.ctx.exio.app.find.qmm.taxexemptlimit;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.qmm.taxexemptlimit.TaxExemptLimit;

/**
* 非課税限度額の登録
*/
@AllArgsConstructor
@Value
public class TaxExemptLimitDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
     * 非課税限度額コード
     */
     private String taxFreeamountCode;
    
    /**
    * 非課税限度額名称
    */
    private String taxExemptionName;
    
    /**
    * 非課税限度額
    */
    private int taxExemption;
    
    
    public static TaxExemptLimitDto fromDomain(TaxExemptLimit domain)
    {
        return new TaxExemptLimitDto(
        		domain.getCid(),
        		domain.getTaxFreeamountCode().v(), 
        		domain.getTaxExemptionName().v(),
        		domain.getTaxExemption().v());
    }
    
}
