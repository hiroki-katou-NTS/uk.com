package nts.uk.ctx.at.shared.app.command.scherec.monthlyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateMonthlyItemControlByAuthCmdHandler extends CommandHandler<MonthlyItemControlByAuthCmd> {
	
	@Inject 
	private MonthlyItemControlByAuthRepository repo;

	@Override
	protected void handle(CommandHandlerContext<MonthlyItemControlByAuthCmd> context) {
		String companyID = AppContexts.user().companyId();
		MonthlyItemControlByAuthCmd command = context.getCommand();
		command.setCompanyId(companyID);
		Optional<MonthlyItemControlByAuthority> data =repo.getMonthlyAttdItem(companyID, command.getAuthorityMonthlyId());
		if(data.isPresent()) {
			repo.updateMonthlyAttdItemAuth(MonthlyItemControlByAuthCmd.fromCommand(command));
		}else {
			repo.addMonthlyAttdItemAuth(MonthlyItemControlByAuthCmd.fromCommand(command));
		}
	}
	
	

}
