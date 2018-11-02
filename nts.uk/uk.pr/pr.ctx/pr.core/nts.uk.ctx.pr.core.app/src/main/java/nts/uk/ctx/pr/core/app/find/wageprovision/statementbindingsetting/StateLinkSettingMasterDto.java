package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;

/**
* 明細書紐付け設定（マスタ）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSettingMasterDto {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタコード
    */
    private String masterCode;
    
    /**
    * 給与明細書
    */
    private String salaryCode;
    
    /**
    * 賞与明細書
    */
    private String bonusCode;

    private String bonusName;

    private String salaryName;
    
    
    public static StateLinkSettingMasterDto fromDomain(StateLinkSettingMaster domain, List<StatementLayout> specificationLayout) {
        Optional<StatementLayout> tempSalary = specificationLayout.stream().filter(item ->item.getStatementCode().v().equals(domain.getSalaryCode().get().v())).findFirst();
        Optional<StatementLayout> tempBonus = specificationLayout.stream().filter(item ->item.getStatementCode().v() == domain.getBonusCode().get().v()).findFirst();
        return new StateLinkSettingMasterDto(
                domain.getHistoryID(),
                domain.getMasterCode().v(),
                domain.getSalaryCode().isPresent() ? domain.getSalaryCode().get().v() : null,
                domain.getBonusCode().isPresent() ? domain.getBonusCode().get().v() : null,
                tempBonus.isPresent() ? tempBonus.get().getStatementName().v() : null,
                tempSalary.isPresent() ? tempSalary.get().getStatementName().v() : null);
    }

}
