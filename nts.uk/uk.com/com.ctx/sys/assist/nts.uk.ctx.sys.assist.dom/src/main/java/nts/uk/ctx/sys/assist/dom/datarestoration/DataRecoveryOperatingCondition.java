package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;

/**
 * 動作状態
 */
@AllArgsConstructor
public enum DataRecoveryOperatingCondition {

	/**
	 * ファイル読取中
	 */
	FILE_READING_IN_PROGRESS (0 , "Enum_OperatingCondition_INPROGRESS"),

	/**
	 * 中断終了
	 */
	INTERRUPTION_END         (1 , "Enum_OperatingCondition_INTERRUPTION_END"),

	/**
	 * 削除中
	 */
	DELETING                 (2 , "Enum_OperatingCondition_DELETING"),

	/**
	 * 完了
	 */
	DONE                     (3 , "Enum_OperatingCondition_DONE"),

	/**
	 * 準備中
	 */
	IN_PREPARATION           (4 , "Enum_OperatingCondition_INPREPARATION"),

	/**
	 * 異常終了
	 */
	ABNORMAL_TERMINATION     (5 , "Enum_OperatingCondition_ABNORMAL_TERMINATION");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
}
