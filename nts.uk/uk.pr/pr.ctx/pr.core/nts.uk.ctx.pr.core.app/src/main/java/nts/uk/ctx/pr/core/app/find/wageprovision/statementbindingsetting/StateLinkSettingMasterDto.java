package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;

/**
* 明細書紐付け設定（マスタ）: DTO
*/
@Value
public class StateLinkSettingMasterDto {
    

    private String historyID;

    private String masterCode;

    private String categoryName;

    private String salaryCode;

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

    public StateLinkSettingMasterDto(String historyID, String masterCode, String categoryName, String salaryCode, String bonusCode, String bonusName, String salaryName) {
        this.historyID = historyID;
        this.masterCode = masterCode;
        this.categoryName = categoryName;
        this.salaryCode = salaryCode;
        this.bonusCode = bonusCode;
        this.bonusName = bonusName;
        this.salaryName = salaryName;
    }

    public StateLinkSettingMasterDto(String historyID, String masterCode, String salaryCode, String bonusCode, String bonusName, String salaryName) {
        this.historyID = historyID;
        this.masterCode = masterCode;
        this.salaryCode = salaryCode;
        this.categoryName = null;
        this.bonusCode = bonusCode;
        this.bonusName = bonusName;
        this.salaryName = salaryName;
    }
}
