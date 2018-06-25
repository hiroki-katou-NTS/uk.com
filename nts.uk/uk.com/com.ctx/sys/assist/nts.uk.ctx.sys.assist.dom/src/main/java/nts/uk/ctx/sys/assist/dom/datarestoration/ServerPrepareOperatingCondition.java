package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;

/**
 * 動作状態
 */
@AllArgsConstructor
public enum ServerPrepareOperatingCondition {

	/**
	 * アップロード中
	 */
	UPLOADING               (0 , "Enum_OperatingCondition_UPLOADING"),

	/**
	 * アップロード失敗
	 */
	UPLOAD_FAILED           (1 , "Enum_OperatingCondition_UPLOAD_FAILED"),

	/**
	 * アップロード完了
	 */
	UPLOAD_COMPLETED        (2 , "Enum_OperatingCondition_UPLOAD_COMPLETED"),

	/**
	 * アップロード終了
	 */
	UPLOAD_FINISHED         (3 , "Enum_OperatingCondition_UPLOAD_FINISHED"),

	/**
	 * チェック完了
	 */
	CHECK_COMPLETED         (4 , "Enum_OperatingCondition_CHECK_COMPLETED"),

	/**
	 * テーブル一覧異常
	 */
	TABLE_LIST_FAULT        (5 , "Enum_OperatingCondition_TABLE_LIST_FAULT"),

	/**
	 * テーブル項目確認中
	 */
	CHECKING_TABLE_ITEMS    (6 , "Enum_OperatingCondition_CHECKING_TABLE_ITEMS"),

	/**
	 * テーブル項目違い
	 */
	TABLE_ITEM_DIFFERENCE   (7 , "Enum_OperatingCondition_TABLE_ITEM_DIFFERENCE"),

	/**
	 * パスワード違い
	 */
	PASSWORD_DIFFERENCE     (8 , "Enum_OperatingCondition_PASSWORD_DIFFERENCE"),

	/**
	 * ファイル構成エラー
	 */
	FILE_CONFIG_ERROR       (9 , "Enum_OperatingCondition_FILE_CONFIG_ERROR"),

	/**
	 * ファイル構成チェック中
	 */
	CHECKING_FILE_STRUCTURE (10, "Enum_OperatingCondition_CHECKING_FILE_STRUCTURE"),

	/**
	 * 別会社不可
	 */
	NO_SEPARATE_COMPANY     (11, "Enum_OperatingCondition_NO_SEPARATE_COMPANY"),

	/**
	 * 社員一覧異常
	 */
	EM_LIST_ABNORMALITY     (12, "Enum_OperatingCondition_EM_LIST_ABNORMALITY"),

	/**
	 * 解凍中
	 */
	EXTRACTING              (13, "Enum_OperatingCondition_EXTRACTING"),

	/**
	 * 解凍失敗
	 */
	EXTRACTION_FAILED       (14, "Enum_OperatingCondition_EXTRACTION_FAILED"),

	/**
	 * 調査保存不可
	 */
	CAN_NOT_SAVE_SURVEY     (15, "Enum_OperatingCondition_CAN_NOT_SAVE_SURVEY");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
}
