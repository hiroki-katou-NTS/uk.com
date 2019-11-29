package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class StateLinkMasterParam {

    private String hisId;

    private GeneralDate date;

    private int startYearMonth;

}
