package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;


import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class StateCorrelationHisEmployeeSettingDto {

    private String code;

    private String name;

    private String historyID;

    private String masterCode;

    private String salaryCode;

    private String salaryName;

    private String bonusCode;

    private String bonusName;


}
