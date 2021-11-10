package nts.uk.ctx.exio.app.command.exi.execlog;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

@Value
public class ExacExeResultLogCommand {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	private String conditionSetCd;

	/**
	 * 外部受入処理ID
	 */
	private String externalProcessId;

	/**
	 * 実行者ID
	 */
	private String executorId;

	/**
	 * ユーザID
	 */
	private String userId;

	/**
	 * 処理開始日時
	 */
	private GeneralDateTime processStartDatetime;

	/**
	 * 定型区分
	 */
	private int standardAtr;

	/**
	 * 実行形態
	 */
	private int executeForm;

	/**
	 * 対象件数
	 */
	private int targetCount;

	/**
	 * エラー件数
	 */
	private int errorCount;

	/**
	 * ファイル名
	 */
	private String fileName;

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 結果状態
	 */
	private Integer resultStatus;

	/**
	 * 処理終了日時
	 */
	private GeneralDateTime processEndDatetime;

	/**
	 * 処理区分
	 */
	private int processAtr;

	private Long version;

}
