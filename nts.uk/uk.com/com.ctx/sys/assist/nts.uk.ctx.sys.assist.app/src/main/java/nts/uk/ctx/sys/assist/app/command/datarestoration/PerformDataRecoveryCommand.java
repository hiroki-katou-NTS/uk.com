package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class PerformDataRecoveryCommand {
	/**
	 * エラー件数
	 */
	public int errorCount;

	/**
	 * カテゴリカウント
	 */
	public int categoryCnt;
	/**
	 * カテゴリトータルカウント
	 */
	public int categoryTotalCount;
	/**
	 * トータル処理件数
	 */
	public int totalNumOfProcesses;
	/**
	 * 処理ID
	 */
	public String dataRecoveryProcessId;

	/**
	 * 処理件数
	 */
	public int numOfProcesses;
	/**
	 * 処理対象社員コード
	 */
	public String processTargetEmpCode;
	/**
	 * 中断状態
	 */
	public int suspendedState;
	/**
	 * 動作状態
	 */
	public int operatingCondition;
	/**
	 * 復旧日付
	 */
	public GeneralDate recoveryDate;

	// データ復旧の結果
	/**
	 * 会社ID
	 */
	public String cid;
	/**
	 * 保存セットコード
	 */
	public String saveSetCd;
	/**
	 * 実行者
	 */
	public String practitioner;
	/**
	 * 実行結果
	 */
	public String executionResult;
	/**
	 * 開始日時
	 */
	public GeneralDateTime startDateTime;
	/**
	 * 終了日時
	 */
	public GeneralDateTime endDateTime;
	/**
	 * 保存形態
	 */
	public int saveForm;
	/**
	 * 保存名称
	 */
	public String saveName;
}
