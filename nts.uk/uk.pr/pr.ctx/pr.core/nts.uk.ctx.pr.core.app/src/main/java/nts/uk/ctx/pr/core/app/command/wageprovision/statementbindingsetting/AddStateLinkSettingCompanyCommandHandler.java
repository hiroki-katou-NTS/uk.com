package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;

@Stateless
@Transactional

public class AddStateLinkSettingCompanyCommandHandler extends CommandHandler<StateLinkSettingCompanyCommand> {

    @Inject
    private StateLinkSettingCompanyRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StateLinkSettingCompanyCommand> stateLinkSettingCompanyCommand) {
        StateLinkSettingCompanyCommand command = stateLinkSettingCompanyCommand.getCommand();
        repository.add(new StateLinkSettingCompany(command.getHistoryID(), new StatementCode(command.getSalaryCode()),new StatementCode(command.getBonusCode())));
    }
}
