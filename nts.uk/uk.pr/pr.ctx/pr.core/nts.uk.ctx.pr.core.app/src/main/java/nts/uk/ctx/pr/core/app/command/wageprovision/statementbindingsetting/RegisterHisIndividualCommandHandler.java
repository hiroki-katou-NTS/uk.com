package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividualRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RegisterHisIndividualCommandHandler extends CommandHandler<StateLinkSettingIndividualCommand> {
    
    @Inject
    private StateCorrelationHisIndividualRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StateLinkSettingIndividualCommand> context) {
    
    }
}
