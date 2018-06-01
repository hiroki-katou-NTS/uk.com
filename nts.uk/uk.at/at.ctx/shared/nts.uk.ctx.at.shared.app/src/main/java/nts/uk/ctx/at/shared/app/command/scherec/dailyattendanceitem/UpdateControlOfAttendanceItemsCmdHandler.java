package nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateControlOfAttendanceItemsCmdHandler extends CommandHandler<ControlOfAttendanceItemsCmd> {

	@Inject
	private ControlOfAttendanceItemsRepository repo;
	@Override
	protected void handle(CommandHandlerContext<ControlOfAttendanceItemsCmd> context) {
		String companyID = AppContexts.user().companyId();
		ControlOfAttendanceItemsCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<ControlOfAttendanceItems> data = repo.getControlOfAttendanceItem(companyID, command.getItemDailyID());
		if(data.isPresent()) {
			repo.updateControlOfAttendanceItem(ControlOfAttendanceItemsCmd.fromDomain(command));
		}else {
			repo.insertControlOfAttendanceItem(ControlOfAttendanceItemsCmd.fromDomain(command));
		}
		
		
	}

}
