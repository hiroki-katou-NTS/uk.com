package nts.uk.ctx.at.function.dom.processexecution;

import java.math.BigDecimal;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

/**
 * Domain service 更新処理自動実行
 * @author TungVD
 *
 */
public interface ProcessExecutionService {
	
	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.F:実行選択.アルゴリズム.起動時処理.次回実行日時作成処理
	 * @param execTaskSet
	 * @return
	 */
	GeneralDateTime processNextExecDateTimeCreation(ExecutionTaskSetting execTaskSet);
	
	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.F:実行選択.アルゴリズム.起動時処理.過去の実行平均時間を超過しているか
	 * @param companyId
	 * @param updateProcessAutoExec
	 * @param execStartDateTime
	 * @return
	 */
	boolean isPassAverageExecTimeExceeded(String companyId, UpdateProcessAutoExecution updateProcessAutoExec, GeneralDateTime execStartDateTime);
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.アルゴリズム.更新処理自動実行ログ履歴.過去の実行平均時間を超過しているか.過去の実行平均時間を取得する
	 * @param companyId
	 * @param execItemCd
	 * @return
	 */
	BigDecimal getAverageRunTime(String companyId, ExecutionCode execItemCd);
	
}
