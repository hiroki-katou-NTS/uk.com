package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionResult;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorAlarmAtr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock.CancelActualLock;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.closurestatusmng.ClosureStatusMng;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess.MonthlyClosureUpdateLogProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.MonthlyClosureRemainNumProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate.ProcessYearMonthUpdate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.CalcPeriodForClosureProcess;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - <<Domain Service>> 月締め更新Mgrクラス
 *
 */

@Stateless
public class MonthlyUpdateMgr {

	@Inject
	private CancelActualLock cancelLock;

	@Inject
	private ProcessYearMonthUpdate ymUpdate;

	@Inject
	private MonthlyClosureUpdateLogProcess logProcess;

	@Inject
	private MonthlyClosureUpdatePersonLogRepository mClosurePerLogRepo;

	@Inject
	private AttendanceTimeOfMonthlyRepository attTimeMonthlyRepo;

	@Inject
	private MonthlyClosureUpdateErrorInforRepository errorInforRepo;

	@Inject
	private MonthlyClosureRemainNumProcess remainNumberProc;

	@Inject
	private ClosureStatusMng closureSttMng;

	@Inject
	private CalcPeriodForClosureProcess calcPeriod;

	public void monthlyUpdateMgr(String monthlyClosureLogId, List<String> listEmpId, int closureId, YearMonth ym,
			ClosureDate closureDate, DatePeriod period) {
		String companyId = AppContexts.user().companyId();
		for (String empId : listEmpId) {
			processEmployee(monthlyClosureLogId, empId, closureId, ym, closureDate, period);
		}
		cancelLock.cancelActualLock(companyId, closureId);
		ymUpdate.processYmUpdate(companyId, closureId);
		logProcess.monthlyClosureUpdateLogProcess(monthlyClosureLogId);
	}

	// 社員毎に実行される処理
	private void processEmployee(String monthlyClosureLogId, String empId, int closureId, YearMonth ym,
			ClosureDate closureDate, DatePeriod period) {
		String companyId = AppContexts.user().companyId();
		Optional<ClosurePeriod> optClosurePeriod = calcPeriod.algorithm(companyId, empId, closureId);
		if (optClosurePeriod.isPresent()) {
			ClosurePeriod closurePeriod = optClosurePeriod.get();
			MonthlyClosureUpdatePersonLog personLog = new MonthlyClosureUpdatePersonLog(empId, monthlyClosureLogId,
					null, MonthlyClosurePersonExecutionStatus.INCOMPLETE);
			executeProcess(monthlyClosureLogId, empId, closurePeriod);
			logProcess.monthlyClosureUpdatePersonLogProcess(monthlyClosureLogId, empId, personLog);
		} else {
			// TODO: description not clear
			// 「既に締め処理済み」が返ってきた場合 (When "already closed" has been returned)
			MonthlyClosureUpdatePersonLog personLog = new MonthlyClosureUpdatePersonLog(empId, monthlyClosureLogId,
					MonthlyClosurePersonExecutionResult.ALREADY_CLOSURE, MonthlyClosurePersonExecutionStatus.COMPLETE);
			mClosurePerLogRepo.add(personLog);

			// 「対象締め処理期間なし」が返ってきた場合 ("When there is no target closing processing
			// period" returned)
			mClosurePerLogRepo.delete(monthlyClosureLogId, empId);
			// F：進捗確認ダイアログの表示を更新する(F: Update display of progress confirmation
			// dialog)
		}

	}

	// 各処理の実行
	@Transactional
	private void executeProcess(String monthlyClosureLogId, String empId, ClosurePeriod period) {
		for (AggrPeriodEachActualClosure p : period.getAggrPeriods()) {
			AttendanceTimeOfMonthly attTimeMontly = getAttendanceTimeOfMonthly(monthlyClosureLogId, empId, p);
			if (attTimeMontly == null)
				continue;
			monthlyClosureUpdateProc(p, empId);
			// アルゴリズム「月別実績バックアップ」を実行する: not cover this time
		}
	}

	// 月別実績の勤怠時間の取得
	private AttendanceTimeOfMonthly getAttendanceTimeOfMonthly(String monthlyClosureLogId, String empId,
			AggrPeriodEachActualClosure period) {
		Optional<AttendanceTimeOfMonthly> opt = attTimeMonthlyRepo.find(empId, period.getYearMonth(),
				period.getClosureId(), period.getClosureDate());
		if (opt.isPresent()) {
			return opt.get();
		} else {
			MonthlyClosureUpdateErrorInfor errorInfor = new MonthlyClosureUpdateErrorInfor(empId, monthlyClosureLogId,
					"001", TextResource.localize("Msg_1107"), MonthlyClosureUpdateErrorAlarmAtr.ERROR.value);
			errorInforRepo.add(errorInfor);
			return null;
		}
	}

	// 月締め更新処理
	private void monthlyClosureUpdateProc(AggrPeriodEachActualClosure period, String empId) {
		remainNumberProc.remainNumberProcess(period, empId);
		closureSttMng.closureStatusManage(period);
	}

}
