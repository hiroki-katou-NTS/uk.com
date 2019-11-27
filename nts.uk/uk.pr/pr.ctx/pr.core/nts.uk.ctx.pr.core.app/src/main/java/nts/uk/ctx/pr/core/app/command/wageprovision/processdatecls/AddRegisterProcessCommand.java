package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

import java.util.List;

@Value
public class AddRegisterProcessCommand {
    List<CurrProcessDateCommand> currProcessDateCommand;
    List<EmpTiedProYearCommand> empTiedProYearCommand;
}
