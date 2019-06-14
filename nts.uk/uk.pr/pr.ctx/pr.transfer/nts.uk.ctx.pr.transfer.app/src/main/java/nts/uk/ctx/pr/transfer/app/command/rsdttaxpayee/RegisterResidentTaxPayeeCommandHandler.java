package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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
public class RegisterResidentTaxPayeeCommandHandler extends CommandHandler<ResidentTaxPayeeCommand> {

	@Inject
	private ResidentTaxPayeeRepository rsdtTaxPayeeRepo;
	
	@Override
	protected void handle(CommandHandlerContext<ResidentTaxPayeeCommand> context) {
		String companyId = AppContexts.user().companyId();
		ResidentTaxPayeeCommand command = context.getCommand();
		if (!command.isUpdateMode()) {
			if (rsdtTaxPayeeRepo.getResidentTaxPayeeById(companyId, command.getCode()).isPresent()) {
				throw new BusinessException("Msg_3");
			}
			rsdtTaxPayeeRepo.add(command.toDomain(companyId));
		} else {
			rsdtTaxPayeeRepo.update(command.toDomain(companyId));
		}
	}

}
