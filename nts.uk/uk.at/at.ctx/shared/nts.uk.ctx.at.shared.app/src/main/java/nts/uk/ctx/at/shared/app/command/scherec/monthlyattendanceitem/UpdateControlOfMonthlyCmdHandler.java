package nts.uk.ctx.at.shared.app.command.scherec.monthlyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateControlOfMonthlyCmdHandler extends CommandHandler<ControlOfMonthlyCmd> {

	@Inject
	private ControlOfMonthlyItemsRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<ControlOfMonthlyCmd> context) {
		String companyID = AppContexts.user().companyId();
		ControlOfMonthlyCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<ControlOfMonthlyItems> data = repo.getControlOfMonthlyItem(companyID, command.getItemMonthlyID());
		if(data.isPresent()) {
			repo.updateControlOfMonthlyItem(ControlOfMonthlyCmd.toDomain(command));
		}else {
			repo.addControlOfMonthlyItem(ControlOfMonthlyCmd.toDomain(command));
		}
	}

}
