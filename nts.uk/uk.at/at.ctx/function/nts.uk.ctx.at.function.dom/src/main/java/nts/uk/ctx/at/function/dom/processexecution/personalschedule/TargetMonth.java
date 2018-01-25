package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;

/**
 * 更新処理対象月
 */
@AllArgsConstructor
public enum TargetMonth {
	/* システム日付の月 */
	CURRENT_MONTH(0, "システム日付の月"),
	
	/* システム日付の翌月 */
	MONTH_LATER(1, "システム日付の翌月"),
	
	/* システム日付の翌々月 */
	TWO_MONTH_LATER(2,  "システム日付の翌々月");
	
	/** The value. */
	public final int value;
	
	public final String valueName;
}
