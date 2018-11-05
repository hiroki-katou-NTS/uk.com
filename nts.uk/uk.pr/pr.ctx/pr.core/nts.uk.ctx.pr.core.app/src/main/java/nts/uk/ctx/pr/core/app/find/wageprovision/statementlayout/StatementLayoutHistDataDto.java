package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;

@AllArgsConstructor
@Value
public class StatementLayoutHistDataDto {
    private String statementCode;
    private String statementName;
    public String historyId;
    public Integer startMonth;
    public Integer endMonth;
    public StatementLayoutSetDto statementLayoutSet;
}
