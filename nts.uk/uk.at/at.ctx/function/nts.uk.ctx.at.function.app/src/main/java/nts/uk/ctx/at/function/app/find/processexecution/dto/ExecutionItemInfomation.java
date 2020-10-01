package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

/**
 * Output 実行項目情報
 * @author ThinkPro
 *
 */
@Data
@Builder
public class ExecutionItemInfomation {

	/**
	 * 次回実行日時を過ぎているか
	 */
	Boolean isPastNextExecDate;
	
	/**
	 * 実行平均時間を超えているか
	 */
	Boolean isOverAverageExecTime;
	
	/**
	 * 次回実行日時
	 */
	GeneralDateTime nextExecDate;
	
	/**
	 * 更新処理自動実行管理
	 */
	ProcessExecutionLogManage updateProcessAutoExecManage;
	
	/**
	 * 更新処理自動実行ログ
	 */
	ProcessExecutionLog updateProcessAutoExecLog;
	
	/**
	 * 更新処理自動実行
	 */
	ProcessExecution updateProcessAutoExec;
	
	/**
	 * 実行タスク設定
	 */
	ExecutionTaskSetting executionTaskSetting;
	
}
