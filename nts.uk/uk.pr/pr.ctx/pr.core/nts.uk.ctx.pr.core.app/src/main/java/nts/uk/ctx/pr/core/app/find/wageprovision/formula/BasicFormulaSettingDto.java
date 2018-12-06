package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicFormulaSetting;

/**
* かんたん計算式設定: DTO
*/
@AllArgsConstructor
@Data
public class BasicFormulaSettingDto
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
    
    
    public static BasicFormulaSettingDto fromDomainToDto(BasicFormulaSetting domain){
        return new BasicFormulaSettingDto( domain.getHistoryID(), domain.getMasterUse().map(i->i.value).orElse(null), domain.getMasterBranchUse().value);
    }
    
}
