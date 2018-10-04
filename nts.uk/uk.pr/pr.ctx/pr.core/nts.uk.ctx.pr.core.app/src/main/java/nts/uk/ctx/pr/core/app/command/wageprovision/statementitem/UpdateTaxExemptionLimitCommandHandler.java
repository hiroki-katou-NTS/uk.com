package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class UpdateTaxExemptionLimitCommandHandler extends CommandHandler<TaxExemptionLimitCommand> {

    @Inject
    private TaxExemptionLimitRepository updateTaxExemp;

    @Override
    protected void handle(CommandHandlerContext<TaxExemptionLimitCommand> commandHandlerContext) {
        TaxExemptionLimitCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        TaxExemptionLimit data = new TaxExemptionLimit(cid, command.getTaxFreeamountCode(),
                command.getTaxExemptionName(), command.getTaxExemption());
        updateTaxExemp.update(data);
    }
}
