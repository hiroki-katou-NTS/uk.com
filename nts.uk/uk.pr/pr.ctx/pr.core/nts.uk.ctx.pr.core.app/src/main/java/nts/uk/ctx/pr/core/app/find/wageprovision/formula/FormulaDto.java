package nts.uk.ctx.pr.core.app.find.wageprovision.formula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* 計算式: DTO
*/
@AllArgsConstructor
@Data
public class FormulaDto {

    public FormulaDto(String companyId, String formulaCode, String formulaName, int settingMethod, Integer nestedAtr) {
        this.companyId = companyId;
        this.formulaCode = formulaCode;
        this.formulaName = formulaName;
        this.settingMethod = settingMethod;
        this.nestedAtr = nestedAtr;
    }

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

    private List<YearMonthHistoryItemDto> history = new ArrayList<>();

    public static FormulaDto fromDomainToDto(Formula domain) {
        return new FormulaDto(domain.getCompanyId(), domain.getFormulaCode().v(), domain.getFormulaName().v(), domain.getSettingMethod().value, domain.getNestedAtr().map(i -> i.value).orElse(null));
    }

    public void setFormulaHistory(List<YearMonthHistoryItem> history) {
        this.history = history.stream().map(YearMonthHistoryItemDto::fromDomainToDto).collect(Collectors.toList());
    }
}