package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class StateLinkSettingIndividualCommand {

    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;
    

}
