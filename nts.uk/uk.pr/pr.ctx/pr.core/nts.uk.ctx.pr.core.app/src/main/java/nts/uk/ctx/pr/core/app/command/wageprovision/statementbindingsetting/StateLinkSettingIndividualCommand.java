package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

@Value
public class StateLinkSettingIndividualCommand {

    private String empId;

    private String hisId;

    private String salary;

    private String bonus;

    private Integer start;

    private Integer end;

    private int mode;
    

}
