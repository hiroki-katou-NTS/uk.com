package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock.CancelActualLock;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess.MonthlyClosureUpdateLogProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr.MonthlyUpdateMgr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate.ProcessYearMonthUpdate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class ExecuteMonthlyClosureCommandHandler extends AsyncCommandHandler<MonthlyClosureResponse> {

	@Inject
	private RecordDomRequireService requireService;

	@Override
	protected void handle(CommandHandlerContext<MonthlyClosureResponse> context) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		List<AtomTask> atomTasks = new ArrayList<>();
		
		MonthlyClosureResponse params = context.getCommand();
		String companyId = AppContexts.user().companyId();
		TaskDataSetter dataSetter = context.asAsync().getDataSetter();
		int count = 0;
		dataSetter.setData("processed", count);
		for (String empId : params.getListEmployeeId()) {
			val atomTask = MonthlyUpdateMgr.processEmployee(require, cacheCarrier,
					params.getMonthlyClosureUpdateLogId(), empId, params.getClosureId(),
					new YearMonth(params.getCurrentMonth()),
					new ClosureDate(params.getClosureDay(), params.getIsLastDayOfMonth()),
					new DatePeriod(params.getPeriodStart(), params.getPeriodEnd()));
			
			atomTasks.add(atomTask);
			
			dataSetter.updateData("processed", ++count);
		}
		
		atomTasks.add(CancelActualLock.cancelActualLock(require, companyId, params.getClosureId()));
		atomTasks.add(ProcessYearMonthUpdate.processYmUpdate(require, companyId, params.getClosureId()));
		atomTasks.add(MonthlyClosureUpdateLogProcess
				.monthlyClosureUpdateLogProcess(require, params.getMonthlyClosureUpdateLogId()));
		
		transaction.allInOneTransaction(atomTasks);
	}

}
