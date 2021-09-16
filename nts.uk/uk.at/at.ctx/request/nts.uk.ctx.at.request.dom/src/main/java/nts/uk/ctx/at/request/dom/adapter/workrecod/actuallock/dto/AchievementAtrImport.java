package nts.uk.ctx.at.request.dom.adapter.workrecod.actuallock.dto;

import lombok.AllArgsConstructor;
/**
 * 実績区分
 * @author tutk
 *
 */
@AllArgsConstructor
public enum AchievementAtrImport {
	/* 日別 */
	DAILY(0, "日別"),
	/* 月別*/
	MONTHLY(1, "月別");
	
	public final int value;
	
	/** The name id. */
	public final String nameId;

	private final static AchievementAtrImport[] values = AchievementAtrImport.values();
	
	public static AchievementAtrImport valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AchievementAtrImport val : AchievementAtrImport.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
