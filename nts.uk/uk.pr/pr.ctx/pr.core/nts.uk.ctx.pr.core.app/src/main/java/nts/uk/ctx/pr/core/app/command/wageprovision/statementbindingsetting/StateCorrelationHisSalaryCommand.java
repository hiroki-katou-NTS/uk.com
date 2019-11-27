package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;
import java.util.List;

@Value
public class StateCorrelationHisSalaryCommand {

    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;

    private int mode;

    private List<StateLinkSettingMasterCommand> stateLinkSettingMaster;
    

}
