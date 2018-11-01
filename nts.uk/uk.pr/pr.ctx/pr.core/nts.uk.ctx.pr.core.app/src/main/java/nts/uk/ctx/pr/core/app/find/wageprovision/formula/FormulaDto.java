package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;

/**
 * 計算式
 */
@Getter
@Setter
@NoArgsConstructor
public class FormulaDto {

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

    public static FormulaDto fromDomain(Formula domain) {
        FormulaDto dto = new FormulaDto();
        dto.setFormulaCode(domain.getFormulaCode().v());
        dto.setFormulaName(domain.getFormulaName().v());
        dto.setSettingMethod(domain.getSettingMethod().value);
        dto.setNestedAtr(domain.getNestedAtr().map(x -> x.value).orElse(null));
        return dto;
    }
}
