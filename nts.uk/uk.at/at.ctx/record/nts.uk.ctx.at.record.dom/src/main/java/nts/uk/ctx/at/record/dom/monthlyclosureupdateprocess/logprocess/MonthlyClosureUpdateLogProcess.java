package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureCompleteStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionResult;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorAlarmAtr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;

/**
 * 
 * @author HungTT - <<Work>> 月締め更新ログ処理
 *
 */

@Stateless
public class MonthlyClosureUpdateLogProcess {

	@Inject
	private MonthlyClosureUpdateLogRepository mClosureUpdateLogRepo;

	@Inject
	private MonthlyClosureUpdatePersonLogRepository mClosureUpdatePerLogRepo;

	@Inject
	private MonthlyClosureUpdateErrorInforRepository mClosureErrorInforRepo;

	/**
	 * 月締め更新ログ処理
	 * 
	 * @param monthlyClosureUpdateLogId
	 */
	public void monthlyClosureUpdateLogProcess(String monthlyClosureUpdateLogId) {
		MonthlyClosureUpdateLog updateLog = mClosureUpdateLogRepo.getLogById(monthlyClosureUpdateLogId).get();
		switch (checkExecutionResult(monthlyClosureUpdateLogId)) {
		case COMPLETE: // アラームなし - no alarm
			updateLog.updateCompleteStatus(MonthlyClosureCompleteStatus.COMPLETE);
			break;
		case COMPLETE_WITH_ERROR: // エラーあり - there is error
			updateLog.updateCompleteStatus(MonthlyClosureCompleteStatus.COMPLETE_WITH_ERROR);
			break;
		case COMPLETE_WITH_ALARM: // アラームあり - there is alarm
			updateLog.updateCompleteStatus(MonthlyClosureCompleteStatus.COMPLETE_WITH_ALARM);
			break;
		default:
			break;
		}
		updateLog.updateExecuteStatus(MonthlyClosureExecutionStatus.COMPLETED_NOT_CONFIRMED);
		mClosureUpdateLogRepo.updateStatus(updateLog);
	}

	private MonthlyClosureCompleteStatus checkExecutionResult(String monthlyClosureUpdateLogId) {
		List<MonthlyClosureUpdatePersonLog> listPersonLog = mClosureUpdatePerLogRepo.getAll(monthlyClosureUpdateLogId);
		List<MonthlyClosureUpdatePersonLog> listPersonLogError = listPersonLog.stream()
				.filter(item -> item.getExecutionResult() == MonthlyClosurePersonExecutionResult.NOT_UPDATED_WITH_ERROR)
				.collect(Collectors.toList());
		if (!listPersonLogError.isEmpty())
			// エラーあり - there is error
			return MonthlyClosureCompleteStatus.COMPLETE_WITH_ERROR;
		List<MonthlyClosureUpdatePersonLog> listPersonLogAlarm = listPersonLog.stream()
				.filter(item -> item.getExecutionResult() == MonthlyClosurePersonExecutionResult.UPDATED_WITH_ALARM)
				.collect(Collectors.toList());
		if (!listPersonLogAlarm.isEmpty())
			// アラームあり - there is alarm
			return MonthlyClosureCompleteStatus.COMPLETE_WITH_ALARM;
		// アラームなし - no alarm
		return MonthlyClosureCompleteStatus.COMPLETE;
	}

	/**
	 * 月締め更新対象者ログ処理
	 * 
	 * @param monthlyClosureUpdateLogId
	 * @param employeeId
	 */
	public void monthlyClosureUpdatePersonLogProcess(String monthlyClosureUpdateLogId, String employeeId,
			MonthlyClosureUpdatePersonLog personLog) {
		List<MonthlyClosureUpdateErrorInfor> optErrorInfor = mClosureErrorInforRepo
				.getByLogIdAndEmpId(monthlyClosureUpdateLogId, employeeId);
		if (optErrorInfor.isEmpty()) {
			personLog.updateResult(MonthlyClosurePersonExecutionResult.UPDATED);
		} else {
			optErrorInfor = optErrorInfor.stream().filter(it -> it.getAtr() == MonthlyClosureUpdateErrorAlarmAtr.ERROR)
					.collect(Collectors.toList());
			if (optErrorInfor.isEmpty()) {
				personLog.updateResult(MonthlyClosurePersonExecutionResult.UPDATED_WITH_ALARM);
			} else {
				personLog.updateResult(MonthlyClosurePersonExecutionResult.NOT_UPDATED_WITH_ERROR);
			}
		}

		personLog.updateStatus(MonthlyClosurePersonExecutionStatus.COMPLETE);
		mClosureUpdatePerLogRepo.add(personLog);
	}

}