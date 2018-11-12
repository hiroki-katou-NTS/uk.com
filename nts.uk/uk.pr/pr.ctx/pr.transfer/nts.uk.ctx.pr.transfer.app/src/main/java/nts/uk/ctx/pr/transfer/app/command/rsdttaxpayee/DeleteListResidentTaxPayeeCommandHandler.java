package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class DeleteListResidentTaxPayeeCommandHandler extends CommandHandler<List<String>> {

	@Inject
	private ResidentTaxPayeeRepository rsdtTaxPayeeRepo;
	
	@Override
	protected void handle(CommandHandlerContext<List<String>> context) {
		String companyId = AppContexts.user().companyId();
		Set<String> setCodes = new HashSet<>(context.getCommand());
		for (String code : setCodes) {
			rsdtTaxPayeeRepo.remove(companyId, code);
		}
	}

}
