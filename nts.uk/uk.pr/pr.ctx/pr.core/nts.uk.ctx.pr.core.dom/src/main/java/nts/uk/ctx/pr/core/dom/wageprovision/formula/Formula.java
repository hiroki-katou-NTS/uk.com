package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 計算式
*/
@Getter
public class Formula extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * 計算式名
    */
    private FormulaName formulaName;
    
    /**
    * 計算式コード
    */
    private FormulaCode formulaCode;
    
    /**
    * 計算式の設定方法
    */
    private FormulaSettingMethod settingMethod;
    
    /**
    * 入れ子利用区分
    */
    private Optional<NestedUseCls> nestedAtr;
    
    public Formula(String companyId, String formulaName, String formulaCode, int settingMethod, Integer nestedAtr) {
        this.companyId = companyId;
        this.formulaName = new FormulaName(formulaName);
        this.formulaCode = new FormulaCode(formulaCode);
        this.settingMethod = EnumAdaptor.valueOf(settingMethod, FormulaSettingMethod.class);
        this.nestedAtr = nestedAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(nestedAtr, NestedUseCls.class));
    }
    
}
