package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr.MonthlyUpdateMgr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
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

	@Override
	protected void handle(CommandHandlerContext<MonthlyClosureResponse> context) {
		MonthlyClosureResponse params = context.getCommand();

		monthlyUpdateService.monthlyUpdateMgr(params.getMonthlyClosureUpdateLogId(), params.getListEmployeeId(),
				params.getClosureId(), new YearMonth(params.getCurrentMonth()),
				new ClosureDate(params.getClosureDay(), params.isLastDayOfMonth()),
				new DatePeriod(params.getPeriodStart(), params.getPeriodEnd()));

	}

}
