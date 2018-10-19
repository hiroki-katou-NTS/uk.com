package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AddTaxExemptionLimitCommandHandler extends CommandHandler<TaxExemptionLimitCommand> {

    @Inject
    private TaxExemptionLimitRepository addTaxExemp;

    @Override
    protected void handle(CommandHandlerContext<TaxExemptionLimitCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        TaxExemptionLimitCommand command = commandHandlerContext.getCommand();
        TaxExemptionLimit taxExemptionLimit = new TaxExemptionLimit(cid, command.getTaxFreeamountCode(),
                command.getTaxExemptionName(), command.getTaxExemption());
        if (addTaxExemp.getTaxExemptLimitById(cid, command.getTaxFreeamountCode()).isPresent()){
            throw new BusinessException("Msg_3");
        }
        addTaxExemp.add(taxExemptionLimit);
    }
}
