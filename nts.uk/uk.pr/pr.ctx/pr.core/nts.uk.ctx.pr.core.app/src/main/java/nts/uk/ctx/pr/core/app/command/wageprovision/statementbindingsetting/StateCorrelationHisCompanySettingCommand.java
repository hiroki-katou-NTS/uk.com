package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;


import lombok.Value;

@Value
public class StateCorrelationHisCompanySettingCommand {
    StateCorrelationHisCompanyCommand stateCorrelationHisCompanyCommand;

    StateLinkSettingCompanyCommand stateLinkSettingCompanyCommand;

    int mode;
}
