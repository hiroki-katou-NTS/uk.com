package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * Output 実行項目情報
 * @author TungVD
 *
 */
@Data
@Builder
public class ExecutionItemInfomationDto {
	
	String execItemCd;

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
	 * 更新処理自動実行
	 */
	UpdateProcessAutoExecutionDto updateProcessAutoExec;
	
	/**
	 * 更新処理自動実行ログ
	 */
	ProcessExecutionLogDto updateProcessAutoExecLog;
	
	/**
	 * 更新処理自動実行管理
	 */
	ProcessExecutionLogManageDto updateProcessAutoExecManage;
	
	/**
	 * 実行タスク設定
	 */
	ExecutionTaskSettingDto executionTaskSetting;
	
}
