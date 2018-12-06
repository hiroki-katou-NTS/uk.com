package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;

/**
* かんたん計算式設定: DTO
*/
@AllArgsConstructor
@Data
public class BasicFormulaSettingCommand
{

    /**
     * 履歴ID
     */
    private String historyID;
    
    /**
    * 使用マスタ
    */
    private Integer masterUse;
    
    /**
    * マスタ分岐利用
    */
    private int masterBranchUse;
    
    
    public BasicFormulaSetting fromCommandToDomain(){
        return new BasicFormulaSetting(historyID, masterUse, masterBranchUse);
    }
    
}
