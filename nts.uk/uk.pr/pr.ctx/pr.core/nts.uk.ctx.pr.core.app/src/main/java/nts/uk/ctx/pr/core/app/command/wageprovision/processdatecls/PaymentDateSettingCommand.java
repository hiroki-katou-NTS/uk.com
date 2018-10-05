package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class PaymentDateSettingCommand {
    SetDaySupportCommand setDaySupportCommand;
    SpecPrintYmSetCommand specPrintYmSetCommand;
}
