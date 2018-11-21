package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParamOptions;

/**
* 給与汎用パラメータ選択肢: DTO
*/
@AllArgsConstructor
@Value
public class SalGenParamOptionsDto
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
    * 選択肢No
    */
    private int optionNo;
    
    /**
    * 選択肢名称
    */
    private String optionName;
    
    
    public static SalGenParamOptionsDto fromDomain(SalGenParamOptions domain)
    {
        return new SalGenParamOptionsDto(domain.getParaNo(), domain.getCID(), domain.getOptionNo(), domain.getOptionName());
    }
    
}
