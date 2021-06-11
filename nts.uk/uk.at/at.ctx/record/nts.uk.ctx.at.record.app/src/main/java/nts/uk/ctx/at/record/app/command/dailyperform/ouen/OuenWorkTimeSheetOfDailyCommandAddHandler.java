package nts.uk.ctx.at.record.app.command.dailyperform.ouen;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class OuenWorkTimeSheetOfDailyCommandAddHandler extends CommandFacade<OuenWorkTimeSheetOfDailyCommand> {

	@Inject
	private OuenWorkTimeSheetOfDailyRepo repo;

	@Override
	protected void handle(CommandHandlerContext<OuenWorkTimeSheetOfDailyCommand> context) {
		OuenWorkTimeSheetOfDailyCommand command = context.getCommand();
		if (!command.getData().isEmpty()) {
			OuenWorkTimeSheetOfDaily domain = new OuenWorkTimeSheetOfDaily(command.getEmployeeId(), command.getWorkDate(), command.getData());
			ArrayList<OuenWorkTimeSheetOfDaily> domains = new ArrayList<>();
			domains.add(domain);
			repo.insert(domains);
		}
	}
}
