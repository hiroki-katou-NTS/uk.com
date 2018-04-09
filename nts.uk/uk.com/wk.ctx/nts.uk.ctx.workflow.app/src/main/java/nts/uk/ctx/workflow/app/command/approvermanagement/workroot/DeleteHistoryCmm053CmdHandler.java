package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.service.DeleteHistoryCmm053Service;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteHistoryCmm053CmdHandler extends CommandHandler<HistoryCmm053Command> {

	@Inject
	DeleteHistoryCmm053Service deleteHistoryCmm053Service;

	@Override
	protected void handle(CommandHandlerContext<HistoryCmm053Command> context) {
		HistoryCmm053Command command = context.getCommand();
		GeneralDate startDate = command.getStartDate();
		String companyId      = AppContexts.user().companyId();
		String employeeId     = command.getEmployeeId();
		GeneralDate endDate   = command.getEndDate();
		this.deleteHistoryCmm053Service.deleteHistoryByManagerSetting(startDate, endDate, companyId, employeeId);
	}
}
