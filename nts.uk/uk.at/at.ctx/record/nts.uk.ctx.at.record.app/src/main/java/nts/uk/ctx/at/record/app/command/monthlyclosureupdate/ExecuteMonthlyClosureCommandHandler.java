package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock.CancelActualLock;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess.MonthlyClosureUpdateLogProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr.MonthlyUpdateMgr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate.ProcessYearMonthUpdate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class ExecuteMonthlyClosureCommandHandler extends AsyncCommandHandler<MonthlyClosureResponse> {

	@Inject
	private MonthlyUpdateMgr monthlyUpdateService;

	@Inject
	private CancelActualLock cancelLock;

	@Inject
	private ProcessYearMonthUpdate ymUpdate;

	@Inject
	private MonthlyClosureUpdateLogProcess logProcess;

	@Override
	protected void handle(CommandHandlerContext<MonthlyClosureResponse> context) {
		MonthlyClosureResponse params = context.getCommand();
		String companyId = AppContexts.user().companyId();
		TaskDataSetter dataSetter = context.asAsync().getDataSetter();
		int count = 0;
		dataSetter.setData("processed", count);
		for (String empId : params.getListEmployeeId()) {
			monthlyUpdateService.processEmployee(params.getMonthlyClosureUpdateLogId(), empId, params.getClosureId(),
					new YearMonth(params.getCurrentMonth()),
					new ClosureDate(params.getClosureDay(), params.getIsLastDayOfMonth()),
					new DatePeriod(params.getPeriodStart(), params.getPeriodEnd()));
			dataSetter.updateData("processed", ++count);
		}
		cancelLock.cancelActualLock(companyId, params.getClosureId());
		ymUpdate.processYmUpdate(companyId, params.getClosureId());
		logProcess.monthlyClosureUpdateLogProcess(params.getMonthlyClosureUpdateLogId());
	}

}
