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
    private String companyId;
    
    /**
    * 計算式コード
    */
    private String formulaCode;
    
    /**
    * 計算式名
    */
    private String formulaName;
    
    /**
    * 計算式の設定方法
    */
    private int settingMethod;
    
    /**
    * 入れ子利用区分
    */
    private Integer nestedAtr;

    private FormulaSettingCommand formulaSettingCommand;

    public Formula fromCommandToDomain(){
        return new Formula(companyId, formulaCode, formulaName, settingMethod, nestedAtr);
    }
}
