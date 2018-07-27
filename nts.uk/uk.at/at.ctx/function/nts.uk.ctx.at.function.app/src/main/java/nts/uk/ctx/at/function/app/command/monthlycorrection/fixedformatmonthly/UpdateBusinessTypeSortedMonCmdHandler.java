package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMon;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateBusinessTypeSortedMonCmdHandler extends  CommandHandler<BusinessTypeSortedMonCmd>{

	@Inject
	private BusinessTypeSortedMonRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<BusinessTypeSortedMonCmd> context) {
		String companyID = AppContexts.user().companyId();
		BusinessTypeSortedMonCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<BusinessTypeSortedMon> data = repo.getOrderReferWorkType(companyID);
		if(data.isPresent())
			repo.updateBusinessTypeSortedMon(BusinessTypeSortedMonCmd.fromCommand(command));
		else
			repo.addBusinessTypeSortedMon(BusinessTypeSortedMonCmd.fromCommand(command));
		
	}
	

}
