package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

import java.util.List;

@Value
public class ListStateLinkSettingMasterCommand {

    List<StateLinkSettingMasterCommand> listStateLinkSettingMasterCommand;
    StateLinkSettingDateCommand stateLinkSettingDateCommand;
    StateCorrelationHisDeparmentCommand stateCorrelationHisDeparmentCommand;
    int mode;
}
