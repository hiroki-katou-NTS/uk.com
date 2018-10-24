package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValue;

/**
* 給与汎用パラメータ値: DTO
*/
@AllArgsConstructor
@Value
public class SalGenParaValueDto
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 選択肢
    */
    private Integer selection;
    
    /**
    * 有効区分
    */
    private int availableAtr;
    
    /**
    * 値（数値）
    */
    private String numValue;
    
    /**
    * 値（文字）
    */
    private String charValue;
    
    /**
    * 値（時間）
    */
    private Integer timeValue;
    
    /**
    * 対象区分
    */
    private Integer targetAtr;
    
    
    public static SalGenParaValueDto fromDomain(SalGenParaValue domain)
    {
        return new SalGenParaValueDto(domain.getHistoryId(),
                domain.getSelection().map(i->i.intValue()).orElse(null),
                domain.getAvailableAtr().value,
                domain.getNumValue().map(i->i.v()).orElse(null),
                domain.getCharValue().map(i->i.v()).orElse(null),
                domain.getTimeValue().map(i->i.v()).orElse(null),
                domain.getTargetAtr().map(i->i.value).orElse(null)
        );
    }
    
}
