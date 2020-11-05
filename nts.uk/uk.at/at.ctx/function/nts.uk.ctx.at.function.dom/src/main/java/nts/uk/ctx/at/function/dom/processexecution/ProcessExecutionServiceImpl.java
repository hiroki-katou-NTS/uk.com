package nts.uk.ctx.at.function.dom.processexecution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.shr.com.task.schedule.UkJobScheduler;

@Stateless
public class ProcessExecutionServiceImpl implements ProcessExecutionService {
	
	private static final BigDecimal BIG_DECIMAL_100 = new BigDecimal(100);
	
	private static final BigDecimal BIG_DECIMAL_120 = new BigDecimal(120);
	
	@Inject
	private ProcessExecutionLogHistRepository processExecLogHistoryRepo;
	
	@Inject
	private UkJobScheduler scheduler;

	@Override
	public GeneralDateTime processNextExecDateTimeCreation(ExecutionTaskSetting execTaskSet) {
		GeneralDateTime nextExecDateTime = null;
		// アルゴリズム「スケジュールされたバッチ処理の次回実行日時を取得する」を実行する
		Optional<GeneralDateTime> oNextExecScheduleDateTime = execTaskSet.getScheduleId()
				// ・次回実行日時（スケジュールID）
				.map((scheduleId) -> this.scheduler.getNextFireTime(scheduleId))	
				.orElse(null);
		Optional<GeneralDateTime> oNextExecEndScheduleDateTime = execTaskSet.getScheduleId()
				// ・次回実行日時（1日の繰り返しスケジュールID）
				.map((endScheduleId) -> this.scheduler.getNextFireTime(endScheduleId))	
				.orElse(null);
		
		// 「次回実行日時（スケジュールID）」をチェックする
		if (!oNextExecScheduleDateTime.isPresent()) {
			// 次回実行日時をNULLとする
			return nextExecDateTime;
		}
		
		// 「次回実行日時（1日の繰り返しスケジュールID）」が取得できたか確認する
		GeneralDateTime nextExecScheduleDateTime = oNextExecScheduleDateTime.get();
		if (oNextExecEndScheduleDateTime.isPresent()) {
			// 「次回実行日時（スケジュールID）」が現在のシステム日時を過ぎているか判定する
			if (nextExecScheduleDateTime.after(GeneralDateTime.now())) {
				// 次回実行日時（スケジュールID）　＞　システム日時
				// 次回実行日時（スケジュールID）を次回実行日時とする
				nextExecDateTime = nextExecScheduleDateTime;
			} else {
				// 次回実行日時（スケジュールID）　＜＝　システム日時
				// 次回実行日時（1日の繰り返しスケジュールID）を次回実行日時とする
				nextExecDateTime = oNextExecEndScheduleDateTime.get();
			}
		} else {
			// 次回実行日時（スケジュールID）を次回実行日時とする
			nextExecDateTime = nextExecScheduleDateTime;
		}
		
		// 「次回実行日時（暫定）」が「終了日＋終了時刻」を過ぎているか判定する
		GeneralDateTime endDateTime = null;
		TaskEndDate endDate = execTaskSet.getEndDate();
		if (endDate != null && endDate.getEndDate() != null) {
			TaskEndTime endTime = execTaskSet.getEndTime();
			if (endTime != null && endTime.getEndTimeCls().equals(EndTimeClassification.YES)) {
				// →「実行タスク設定.終了日.終了日」＋「実行タスク設定.終了時刻設定.終了時刻」＝終了日時
				endDateTime = GeneralDateTime.fromString(
						endDate.getEndDate().toString("yyyy/MM/dd") 
								+ " " 
								+ endTime.getEndTime().hour() + ":" + endTime.getEndTime().minute() + ":00",
						"yyyy/MM/dd HH:mm:ss");
			} else {
				// →「実行タスク設定.終了日.終了日」＋0:00＝終了日時
				endDateTime = GeneralDateTime.fromString(
						endDate.getEndDate().toString("yyyy/MM/dd") + " 00:00:00",
						"yyyy/MM/dd HH:mm:ss");
			}
		}
		
		// 終了日時を過ぎている
		if (endDateTime != null && nextExecDateTime.after(endDateTime)) {
			// 次回実行日時をNULLとする
			return null;
		} 
		
		return nextExecDateTime;
	}

	@Override
	public boolean isPassAverageExecTimeExceeded(String companyId, ProcessExecution updateProcessAutoExec,
			GeneralDateTime execStartDateTime) {
		// 過去の実行平均時間を取得する
		BigDecimal averageRunTime = this.getAverageRunTime(companyId, updateProcessAutoExec.getExecItemCd());
		
		// 現在の経過時間と取得した「実行平均時間」を比較する
		BigDecimal currentRunTime = BigDecimal.valueOf(GeneralDateTime.now().seconds() - execStartDateTime.seconds());
		
		// 【比較方法】 システム日時 - INPUT．「実行開始日時」> 取得した「実行平均時間」*120%
		// 【OUTPUT】 boolean（true：超過している/false：超過してない）
		return currentRunTime.compareTo(averageRunTime.multiply(BIG_DECIMAL_120).divide(BIG_DECIMAL_100)) > 0;
	}

	@Override
	public BigDecimal getAverageRunTime(String companyId, ExecutionCode execItemCd) {
		// ドメインモデル「更新処理自動実行ログ履歴」を取得する
		List<ProcessExecutionLogHistory> listHistory = this.processExecLogHistoryRepo.getByCompanyIdAndExecItemCd(companyId, execItemCd.v());
		if (listHistory.isEmpty()) {
			return BigDecimal.ZERO;
		}
		
		// 実行平均時間を計算する
		Integer sumExecutionTime = listHistory.stream()
				.filter(history -> history.getLastEndExecDateTime().isPresent() && history.getLastExecDateTime().isPresent())
				.mapToInt(history -> history.getLastEndExecDateTime().get().seconds() - history.getLastExecDateTime().get().seconds())
				.sum();
		
		// 計算した「実行平均時間」を返す
		return BigDecimal.valueOf(sumExecutionTime)
				.divide(BigDecimal.valueOf(listHistory.size()), RoundingMode.CEILING);
	}

}
