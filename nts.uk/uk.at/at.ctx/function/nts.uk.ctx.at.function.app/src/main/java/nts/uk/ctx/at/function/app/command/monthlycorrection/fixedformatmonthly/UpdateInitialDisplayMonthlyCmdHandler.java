package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateInitialDisplayMonthlyCmdHandler extends CommandHandler<InitialDisplayMonthlyCmd> {

	@Inject
	private InitialDisplayMonthlyRepository repo;

	@Override
	protected void handle(CommandHandlerContext<InitialDisplayMonthlyCmd> context) {
		String companyID = AppContexts.user().companyId();
		InitialDisplayMonthlyCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<InitialDisplayMonthly> checkData = repo.getInitialDisplayMon(companyID, command.getMonthlyPfmFormatCode());
		if(checkData.isPresent())
			repo.updateInitialDisplayMonthly(InitialDisplayMonthlyCmd.fromCommand(command));
		else
			throw new BusinessException("Msg_3");
		
	}
	
	
}
