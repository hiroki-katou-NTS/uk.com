package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

import java.util.List;

@Value
public class StateCorrelationHisEmployeeContainerCommand {
    List<StateLinkSettingMasterCommand> listStateLinkSettingMasterCommand;
    StateCorrelationHisEmployeeCommand stateCorrelationHisEmployeeCommand;
    int mode;
}
