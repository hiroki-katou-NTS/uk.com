package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionResult;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorAlarmAtr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.closurestatusmng.ClosureStatusMng;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess.MonthlyClosureUpdateLogProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.MonthlyClosureRemainNumProcess;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.CalcPeriodForClosureProcValue;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.CalcPeriodForClosureProcess;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author HungTT - <<Domain Service>> 月締め更新Mgrクラス
 *
 */
public class MonthlyUpdateMgr {

	// 社員毎に実行される処理
	public static AtomTask processEmployee(RequireM4 require, CacheCarrier cacheCarrier,
			String monthlyClosureLogId, String empId, int closureId, YearMonth ym,
			ClosureDate closureDate, DatePeriod period) {
		List<AtomTask> atomTask = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		
		CalcPeriodForClosureProcValue calculatedResult = CalcPeriodForClosureProcess.algorithm(require, 
				companyId, empId, closureId);
		//アルゴリズム「締め処理すべき集計期間を計算」を実行する
		switch (calculatedResult.getState()) {
		case EXIST:
			ClosurePeriod closurePeriod = calculatedResult.getClosurePeriod().get();
			MonthlyClosureUpdatePersonLog personLog = new MonthlyClosureUpdatePersonLog(empId, monthlyClosureLogId,
					null, MonthlyClosurePersonExecutionStatus.INCOMPLETE);
			atomTask.add(executeProcess(require, cacheCarrier, monthlyClosureLogId, empId, closurePeriod));
			atomTask.add(MonthlyClosureUpdateLogProcess.monthlyClosureUpdatePersonLogProcess(require, 
					monthlyClosureLogId, empId, personLog));
			break;
			//「対象締め処理期間なし」が返ってきた場合
		case NOT_EXIST:
			atomTask.add(AtomTask.of(() -> require.deleteMonthlyClosureUpdatePersonLog(monthlyClosureLogId, empId)));
			break;
			//「既に締め処理済み」が返ってきた場合
		case PROCESSED:
			MonthlyClosureUpdatePersonLog personLog2 = new MonthlyClosureUpdatePersonLog(empId, monthlyClosureLogId,
					MonthlyClosurePersonExecutionResult.ALREADY_CLOSURE, MonthlyClosurePersonExecutionStatus.COMPLETE);
			atomTask.add(AtomTask.of(() -> require.addMonthlyClosureUpdatePersonLog(personLog2)));
			break;
		}
		
		return AtomTask.bundle(atomTask);
	}

	// 各処理の実行
	private static AtomTask executeProcess(RequireM3 require, CacheCarrier cacheCarrier, String monthlyClosureLogId, String empId, ClosurePeriod period) {
		List<AtomTask> persist = new ArrayList<>();
		
		for (AggrPeriodEachActualClosure p : period.getAggrPeriods()) {
			AttendanceTimeOfMonthly attTimeMontly = getAttendanceTimeOfMonthly(require, persist, 
					monthlyClosureLogId, empId, p);
			
			if (attTimeMontly == null)
				continue;
			
			persist.add(monthlyClosureUpdateProc(require, cacheCarrier, p, empId, attTimeMontly));
			// アルゴリズム「月別実績バックアップ」を実行する: not cover this time
		}
		
		return AtomTask.bundle(persist);
	}

	// 月別実績の勤怠時間の取得
	private static AttendanceTimeOfMonthly getAttendanceTimeOfMonthly(RequireM2 require, List<AtomTask> persist, 
			String monthlyClosureLogId, String empId, AggrPeriodEachActualClosure period) {
		Optional<AttendanceTimeOfMonthly> opt = require.attendanceTimeOfMonthly(empId, period.getYearMonth(),
				period.getClosureId(), period.getClosureDate());
		if (opt.isPresent()) {
			return opt.get();
		} 
		
		MonthlyClosureUpdateErrorInfor errorInfor = new MonthlyClosureUpdateErrorInfor(empId, monthlyClosureLogId,
				period.getPeriod().end(), "001", TextResource.localize("Msg_1107",
						period.getPeriod().start().toString(), period.getPeriod().end().toString()),
				MonthlyClosureUpdateErrorAlarmAtr.ERROR.value);
		
		persist.add(AtomTask.of(() -> require.addMonthlyClosureUpdateErrorInfor(errorInfor)));
		
		return null;
		
	}

	// 月締め更新処理
	private static AtomTask monthlyClosureUpdateProc(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			AttendanceTimeOfMonthly attTimeMonthly) {
		
		return AtomTask.of(() -> {})
					.then(MonthlyClosureRemainNumProcess.remainNumberProcess(require, cacheCarrier, period, empId, attTimeMonthly))
					.then(ClosureStatusMng.closureStatusManage(require, period, empId));
	}
	
	public static interface RequireM4 extends RequireM3, CalcPeriodForClosureProcess.RequireM1,
		MonthlyClosureUpdateLogProcess.RequireM1{
		
		void deleteMonthlyClosureUpdatePersonLog(String monthlyLogId, String empId);
	}
	
	public static interface RequireM3 extends RequireM1, RequireM2 {
	}

	public static interface RequireM2 {
		
		Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate);
		
		void addMonthlyClosureUpdateErrorInfor(MonthlyClosureUpdateErrorInfor domain);
	}
	
	public static interface RequireM1 extends ClosureStatusMng.RequireM1,
		MonthlyClosureRemainNumProcess.RequireM1 {
		
	}

}
