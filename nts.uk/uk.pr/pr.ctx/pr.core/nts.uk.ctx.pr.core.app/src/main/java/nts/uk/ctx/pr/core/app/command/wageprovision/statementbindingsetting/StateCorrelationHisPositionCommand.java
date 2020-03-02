package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class StateCorrelationHisPositionCommand {


    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;

    private GeneralDate baseDate;
    
    private int mode;

    List<StateLinkSettingMasterCommand> stateLinkSettingMaster;
}
