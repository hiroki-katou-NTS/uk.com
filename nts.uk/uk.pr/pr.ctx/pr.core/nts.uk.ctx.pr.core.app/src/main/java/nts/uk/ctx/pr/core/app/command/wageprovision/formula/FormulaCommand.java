package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 計算式: DTO
*/
@AllArgsConstructor
@Data
public class FormulaCommand {

    /**
    * 会社ID
    */
    public String companyId;
    
    /**
    * 計算式コード
    */
    public String formulaCode;
    
    /**
    * 計算式名
    */
    public String formulaName;
    
    /**
    * 計算式の設定方法
    */
    public int settingMethod;
    
    /**
    * 入れ子利用区分
    */
    public Integer nestedAtr;

    public FormulaSettingCommand formulaSettingCommand;

    public Formula fromCommandToDomain(){
        return new Formula(companyId, formulaCode, formulaName, settingMethod, nestedAtr);
    }

    public void updateHistoryIdentifier () {
        formulaSettingCommand.updateIdentifier();
    }
}
