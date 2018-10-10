package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;

/**
* 給与個人単価名称: DTO
*/
@AllArgsConstructor
@Value
public class SalaryPerUnitPriceNameDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private String code;
    
    /**
    * 名称
    */
    private String name;
    
    /**
    * 廃止区分
    */
    private int abolition;
    
    /**
    * 略名
    */
    private String shortName;
    
    /**
    * 統合コード
    */
    private String integrationCode;
    
    /**
    * メモ
    */
    private String note;
    
    
    public static SalaryPerUnitPriceNameDto fromDomain(SalaryPerUnitPriceName domain)
    {
        return new SalaryPerUnitPriceNameDto(domain.getCid(), domain.getCode().v(), domain.getName().v(), domain.getAbolition().value, domain.getShortName().v(), domain.getIntegrationCode().v(), domain.getNote().map(i->i.v()).orElse(null));
    }
    
}
