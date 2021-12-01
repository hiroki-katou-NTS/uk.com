package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse;

import lombok.AllArgsConstructor;
/**
 * 実績区分
 * @author tutk
 *
 */
@AllArgsConstructor
public enum AchievementAtr {
	/* 日別 */
	DAILY(0, "日別"),
	/* 月別*/
	MONTHLY(1, "月別");
	
	public final int value;
	
	/** The name id. */
	public final String nameId;

	private final static AchievementAtr[] values = AchievementAtr.values();
	
	public static AchievementAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AchievementAtr val : AchievementAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
