package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


@Stateless
@Transactional
public class AddStateCorrelationHisCompanyCommandHandler extends CommandHandler<StateCorrelationHisCompanyCommand>
{

    @Inject
    private StateCorrelationHisCompanyService stateCorrelationHisCompanyService;
    
    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisCompanyCommand> context) {
        StateCorrelationHisCompanyCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        stateCorrelationHisCompanyService.addStateCorrelationHisCompany(cid,command.getHistoryID(),new YearMonth(command.getStartYearMonth()),new YearMonth(command.getEndYearMonth()));
    }
}
