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
public class DetailFormulaSettingCommand {

    /**
     * 計算式コード
     */
    public String formulaCode;

    /**
     * 履歴ID
     */
    public String historyID;
    
    /**
    * 参照月
    */
    public int referenceMonth;
    
    /**
    * 端数処理(詳細計算式)
    */
    public int roundingMethod;
    
    /**
    * 端数処理位置
    */
    public int roundingPosition;
    
    /**
    * 計算式要素
    */
    public List<DetailCalculationFormulaCommand> detailCalculationFormula;


    public DetailFormulaSetting fromCommandToDomain (){
        return new DetailFormulaSetting(formulaCode, historyID, referenceMonth, detailCalculationFormula.stream().map(DetailCalculationFormulaCommand::fromCommandToDomain).collect(Collectors.toList()), roundingMethod, roundingPosition);
    }
    
}
