package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RemoveTaxExemptionLimitCommandHandler extends CommandHandler<TaxExemptionLimitCommand> {

    @Inject
    private TaxExemptionLimitRepository removeTaxExemp;

    @Override
    protected void handle(CommandHandlerContext<TaxExemptionLimitCommand> commandHandlerContext) {
        TaxExemptionLimitCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        removeTaxExemp.remove(cid, command.getTaxFreeamountCode());
    }
}
