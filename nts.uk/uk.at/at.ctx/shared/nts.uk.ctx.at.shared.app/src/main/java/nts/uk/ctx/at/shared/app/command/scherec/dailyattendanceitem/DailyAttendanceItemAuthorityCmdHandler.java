package nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyAttendanceItemAuthorityCmdHandler extends CommandHandler<DailyAttendanceItemAuthorityCmd> {

	@Inject
	private DailyAttdItemAuthRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<DailyAttendanceItemAuthorityCmd> context) {
		String companyID = AppContexts.user().companyId();
		DailyAttendanceItemAuthorityCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<DailyAttendanceItemAuthority> data = repo.getDailyAttdItem(companyID, command.getAuthorityDailyId());
		if(data.isPresent()) {
			repo.updateDailyAttdItemAuth(DailyAttendanceItemAuthorityCmd.fromCommand(command));
		}else {
			repo.addDailyAttdItemAuth(DailyAttendanceItemAuthorityCmd.fromCommand(command));
		}
	}

}
