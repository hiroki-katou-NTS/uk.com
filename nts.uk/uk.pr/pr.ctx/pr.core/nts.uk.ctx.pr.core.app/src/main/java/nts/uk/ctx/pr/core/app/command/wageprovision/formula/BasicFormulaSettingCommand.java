package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;

/**
* かんたん計算式設定: DTO
*/
@AllArgsConstructor
@Data
public class BasicFormulaSettingCommand {

    /**
     * 計算式コード
     */
    public String formulaCode;

    /**
     * 履歴ID
     */
    public String historyID;

    /**
    * 使用マスタ
    */
    public Integer masterUse;
    
    /**
    * マスタ分岐利用
    */
    public int masterBranchUse;
    
    
    public BasicFormulaSetting fromCommandToDomain(){
        return new BasicFormulaSetting(formulaCode, historyID, masterBranchUse, masterUse);
    }
    
}
