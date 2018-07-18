package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;

/**
 * 復旧方法
 *
 */
@AllArgsConstructor
public enum RecoveryMethod {

	/**
	 * 全件復旧
	 */
	ALL_CASES_RESTORED     (0, "Enum_RecoveryMethod_ALL_CASES_RESTORED"),

	/**
	 * 選択した範囲で復旧
	 */
	RESTORE_SELECTED_RANGE (1, "Enum_RecoveryMethod_RESTORE_SELECTED_RANGE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
}
