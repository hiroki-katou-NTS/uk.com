package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSetIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;

import java.util.List;
import java.util.Optional;

/**
* 明細書紐付け設定（個人）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSetIndiviDto {

    private String hisId;

    private String salaryCode;

    private String salaryName;

    private String bonusCode;

    private String bonusName;


    public static StateLinkSetIndiviDto fromDomain(StateLinkSetIndivi domain, List<StatementLayout> listStatementLayout) {
        Optional<StatementLayout> tempSalary = listStatementLayout.stream().filter(item ->item.getStatementCode().v().equals(domain.getSalaryCode().get().v())).findFirst();
        Optional<StatementLayout> tempBonus = listStatementLayout.stream().filter(item ->item.getStatementCode().v().equals(domain.getBonusCode().get().v())).findFirst();
        return new StateLinkSetIndiviDto(
                domain.getHistoryID(),
                domain.getSalaryCode().isPresent() ? domain.getSalaryCode().get().v() : null,
                tempSalary.isPresent() ? tempSalary.get().getStatementName().v() : null,
                domain.getBonusCode().isPresent() ? domain.getBonusCode().get().v() : null,
                tempBonus.isPresent() ? tempBonus.get().getStatementName().v() : null);
    }
    
}
