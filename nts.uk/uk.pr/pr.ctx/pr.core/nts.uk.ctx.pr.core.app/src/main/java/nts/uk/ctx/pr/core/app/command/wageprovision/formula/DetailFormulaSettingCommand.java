package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;

import java.util.List;
import java.util.stream.Collectors;

/**
* 詳細計算式: DTO
*/
@AllArgsConstructor
@Data
public class DetailFormulaSettingCommand
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
    private List<DetailCalculationFormulaCommand> detailCalculationFormula;


    public DetailFormulaSetting fromCommandToDomain (){
        return new DetailFormulaSetting(historyID, referenceMonth, detailCalculationFormula.stream().map(DetailCalculationFormulaCommand::fromCommandToDomain).collect(Collectors.toList()), roundingMethod, roundingPosition);
    }
    
}
