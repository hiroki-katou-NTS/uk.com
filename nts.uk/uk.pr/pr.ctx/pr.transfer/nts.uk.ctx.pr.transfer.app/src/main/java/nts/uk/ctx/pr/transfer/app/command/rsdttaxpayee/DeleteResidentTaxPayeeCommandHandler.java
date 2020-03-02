package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DeleteResidentTaxPayeeCommandHandler extends CommandHandler<String> {

	@Inject
	private ResidentTaxPayeeRepository rsdtTaxPayeeRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String companyId = AppContexts.user().companyId();
		rsdtTaxPayeeRepo.remove(companyId, context.getCommand());
	}

}
