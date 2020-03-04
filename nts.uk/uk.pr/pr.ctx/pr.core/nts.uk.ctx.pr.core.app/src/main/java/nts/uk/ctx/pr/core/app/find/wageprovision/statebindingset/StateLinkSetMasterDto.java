package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;

/**
* 明細書紐付け設定（マスタ）: DTO
*/
@Value
public class StateLinkSetMasterDto {
    

    private String historyID;

    private String masterCode;

    private String categoryName;

    private String salaryCode;

    private String bonusCode;

    private String bonusName;

    private String salaryName;
    
    
    public static StateLinkSetMasterDto fromDomain(StateLinkSetMaster domain, List<StatementLayout> specificationLayout) {
        Optional<StatementLayout> tempSalary = Optional.empty();
        Optional<StatementLayout> tempBonus = Optional.empty();
        if(domain.getSalaryCode().isPresent()) {
            tempSalary = specificationLayout.stream().filter(item -> item.getStatementCode().v().equals(domain.getSalaryCode().get().v())).findFirst();
        }
        if(domain.getBonusCode().isPresent()) {
            tempBonus = specificationLayout.stream().filter(item -> item.getStatementCode().v().equals(domain.getBonusCode().get().v())).findFirst();
        }
        return new StateLinkSetMasterDto(
                domain.getHistoryID(),
                domain.getMasterCode().v(),
                domain.getSalaryCode().isPresent() ? domain.getSalaryCode().get().v() : null,
                domain.getBonusCode().isPresent() ? domain.getBonusCode().get().v() : null,
                tempBonus.isPresent() ? tempBonus.get().getStatementName().v() : null,
                tempSalary.isPresent() ? tempSalary.get().getStatementName().v() : null);
    }

    public StateLinkSetMasterDto(String historyID, String masterCode, String categoryName, String salaryCode, String bonusCode, String bonusName, String salaryName) {
        this.historyID = historyID;
        this.masterCode = masterCode;
        this.categoryName = categoryName;
        this.salaryCode = salaryCode;
        this.bonusCode = bonusCode;
        this.bonusName = bonusName;
        this.salaryName = salaryName;
    }

    public StateLinkSetMasterDto(String historyID, String masterCode, String salaryCode, String bonusCode, String bonusName, String salaryName) {
        this.historyID = historyID;
        this.masterCode = masterCode;
        this.salaryCode = salaryCode;
        this.categoryName = null;
        this.bonusCode = bonusCode;
        this.bonusName = bonusName;
        this.salaryName = salaryName;
    }
}
