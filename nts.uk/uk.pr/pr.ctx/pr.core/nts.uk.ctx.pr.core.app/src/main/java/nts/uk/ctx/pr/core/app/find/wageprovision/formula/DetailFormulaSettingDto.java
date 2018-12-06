package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;

import java.util.List;
import java.util.stream.Collectors;

/**
* 詳細計算式: DTO
*/
@AllArgsConstructor
@Data
public class DetailFormulaSettingDto
{

    /**
     * 履歴ID
     */
    private String historyID;
    
    /**
    * 参照月
    */
    private int referenceMonth;
    
    /**
    * 端数処理(詳細計算式)
    */
    private int roundingMethod;
    
    /**
    * 端数処理位置
    */
    private int roundingPosition;
    
    /**
    * 計算式要素
    */
    private List<DetailCalculationFormulaDto> detailCalculationFormula;


    public static DetailFormulaSettingDto fromDomainToDto(DetailFormulaSetting domain){
        return new DetailFormulaSettingDto(domain.getHistoryId(), domain.getReferenceMonth().value, domain.getRoundingMethod().value, domain.getRoundingPosition().value, domain.getDetailCalculationFormula().stream().map(DetailCalculationFormulaDto:: fromDomainToDto).collect(Collectors.toList()));
    }
    
}
