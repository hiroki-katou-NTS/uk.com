package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;

@Stateless
@Transactional
public class UpdateStateLinkSettingCompanyCommandHandler extends CommandHandler<StateCorrelationHisCompanySettingCommand> {
    
    @Inject
    private StateLinkSettingCompanyRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisCompanySettingCommand> context) {
        StateLinkSettingCompanyCommand command = context.getCommand().getStateLinkSettingCompanyCommand();
        repository.update(new StateLinkSettingCompany(command.getHistoryID(),
                command.getSalaryCode() == null? null : new StatementCode(command.getSalaryCode()),
                command.getBonusCode() == null ? null : new StatementCode(command.getBonusCode())));
    
    }
}
