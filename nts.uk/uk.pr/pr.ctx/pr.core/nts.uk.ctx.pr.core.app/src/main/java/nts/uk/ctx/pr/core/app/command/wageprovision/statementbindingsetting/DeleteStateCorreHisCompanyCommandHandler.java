package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteStateCorreHisCompanyCommandHandler extends CommandHandler<StateCorrelationHisCompanyCommand>{

    @Inject
    private StateUseUnitSettingRepository repository;


    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisCompanyCommand> context) {
        StateCorrelationHisCompanyCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();


    }
}
