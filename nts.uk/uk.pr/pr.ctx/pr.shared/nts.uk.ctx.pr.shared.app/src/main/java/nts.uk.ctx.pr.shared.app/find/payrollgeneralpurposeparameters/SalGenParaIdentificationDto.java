package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaIdentification;

/**
* 給与汎用パラメータ識別: DTO
*/
@AllArgsConstructor
@Value
public class SalGenParaIdentificationDto
{
    
    /**
    * パラメータNo
    */
    private String paraNo;
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 名称
    */
    private String name;
    
    /**
    * 属性区分
    */
    private int attributeType;
    
    /**
    * 履歴区分
    */
    private int historyAtr;
    
    /**
    * 補足説明
    */
    private String explanation;
    
    
    public static SalGenParaIdentificationDto fromDomain(SalGenParaIdentification domain)
    {
        return new SalGenParaIdentificationDto(domain.getParaNo(), domain.getCID(), domain.getName(), domain.getAttributeType().value, domain.getHistoryAtr().value, domain.getExplanation().get());
    }
    
}
