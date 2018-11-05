package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class StatementLayoutDto {

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 給与明細書
     */
    private String salaryCode;

    private String salaryLayoutName;


    /**
     * 賞与明細書
     */
    private String bonusCode;

    private String bonusLayoutName;
}
