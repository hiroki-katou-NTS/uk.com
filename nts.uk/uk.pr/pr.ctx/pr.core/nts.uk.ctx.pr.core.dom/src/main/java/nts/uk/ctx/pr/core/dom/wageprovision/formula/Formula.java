package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

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
    * 計算式コード
    */
    private FormulaCode formulaCode;
    
    /**
    * 計算式名
    */
    private FormulaName formulaName;
    
    /**
    * 計算式の設定方法
    */
    private FormulaSettingMethod settingMethod;
    
    /**
    * 入れ子利用区分
    */
    private Optional<NestedUseCls> nestedAtr;
    
    public Formula(String cid, String formulaCode, String formulaName, int settingMethod, Integer nestedAtr) {
        this.companyId = cid;
        this.formulaName = new FormulaName(formulaName);
        this.formulaCode = new FormulaCode(formulaCode);
        this.settingMethod = EnumAdaptor.valueOf(settingMethod, FormulaSettingMethod.class);
        this.nestedAtr = Optional.ofNullable(nestedAtr == null ?  null : EnumAdaptor.valueOf(nestedAtr, NestedUseCls.class));
    }
    
}
