package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureCompleteStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionResult;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosurePersonExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorAlarmAtr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;

/**
 * 
 * @author HungTT - <<Work>> 月締め更新ログ処理
 *
 */
public class MonthlyClosureUpdateLogProcess {
	
	/**
	 * 月締め更新ログ処理
	 * 
	 * @param monthlyClosureUpdateLogId
	 */
	public static AtomTask monthlyClosureUpdateLogProcess(RequireM3 require, String monthlyClosureUpdateLogId) {
		
		MonthlyClosureUpdateLog updateLog = require.monthlyClosureUpdateLog(monthlyClosureUpdateLogId).get();
		
		switch (checkExecutionResult(require, monthlyClosureUpdateLogId)) {
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
		
		return AtomTask.of(() -> require.updateMonthlyClosureUpdateLog(updateLog));
	}

	private static MonthlyClosureCompleteStatus checkExecutionResult(RequireM2 require, String monthlyClosureUpdateLogId) {
		List<MonthlyClosureUpdatePersonLog> listPersonLog = require.monthlyClosureUpdatePersonLog(monthlyClosureUpdateLogId);
		
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
	public static AtomTask monthlyClosureUpdatePersonLogProcess(RequireM1 require, String monthlyClosureUpdateLogId, String employeeId,
			MonthlyClosureUpdatePersonLog personLog) {
		List<MonthlyClosureUpdateErrorInfor> optErrorInfor = require
				.monthlyClosureUpdateErrorInfor(monthlyClosureUpdateLogId, employeeId);
		
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
		
		return AtomTask.of(() -> require.addMonthlyClosureUpdatePersonLog(personLog));
	}

	public static interface RequireM3 extends RequireM2 {
		
		Optional<MonthlyClosureUpdateLog> monthlyClosureUpdateLog(String id);
		
		void updateMonthlyClosureUpdateLog(MonthlyClosureUpdateLog domain);
	}
	
	public static interface RequireM2 {
		
		List<MonthlyClosureUpdatePersonLog> monthlyClosureUpdatePersonLog(String monthlyClosureUpdateLogId);
	}
	
	public static interface RequireM1 {
		
		List<MonthlyClosureUpdateErrorInfor> monthlyClosureUpdateErrorInfor(String monthlyClosureUpdateLogId, String employeeId);

		void addMonthlyClosureUpdatePersonLog(MonthlyClosureUpdatePersonLog domain);
	}
}