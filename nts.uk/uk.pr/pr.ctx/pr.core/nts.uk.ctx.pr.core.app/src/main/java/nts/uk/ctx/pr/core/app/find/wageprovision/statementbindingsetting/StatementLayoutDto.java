package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class StatementLayoutDto {

    private String historyID;

    private String salaryCode;

    private String salaryLayoutName;

    private String bonusCode;

    private String bonusLayoutName;
}
