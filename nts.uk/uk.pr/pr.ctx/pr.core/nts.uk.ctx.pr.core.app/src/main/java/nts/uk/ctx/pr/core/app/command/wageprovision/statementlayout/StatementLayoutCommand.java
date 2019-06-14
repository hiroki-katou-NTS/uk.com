package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class StatementLayoutCommand {
    int isClone;
    String histIdNew;
    String histIdClone;
    int layoutPatternClone;
    String statementCode;
    String statementName;
    int startDate;
    int layoutPattern;
    String statementCodeClone;
}
