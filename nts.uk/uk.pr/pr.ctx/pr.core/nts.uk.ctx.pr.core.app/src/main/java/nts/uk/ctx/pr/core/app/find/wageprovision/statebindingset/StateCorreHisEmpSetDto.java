package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;


import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class StateCorreHisEmpSetDto {

    private String code;

    private String name;

    private String historyID;

    private String masterCode;

    private String salaryCode;

    private String salaryName;

    private String bonusCode;

    private String bonusName;


}
