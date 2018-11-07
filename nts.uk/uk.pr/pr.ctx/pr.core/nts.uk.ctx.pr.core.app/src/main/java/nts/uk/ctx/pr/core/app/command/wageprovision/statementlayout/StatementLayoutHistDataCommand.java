package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class StatementLayoutHistDataCommand {
    private boolean checkCreate;
    private String statementCode;
    private String statementName;
    private String historyId;
    private Integer startMonth;
    private Integer endMonth;
    private StatementLayoutSetCommand statementLayoutSet;
}
