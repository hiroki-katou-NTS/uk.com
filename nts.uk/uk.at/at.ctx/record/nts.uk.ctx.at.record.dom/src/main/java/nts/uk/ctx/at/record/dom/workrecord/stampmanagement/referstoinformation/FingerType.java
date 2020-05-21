package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation;

import lombok.AllArgsConstructor;

/**
 * 	指種類
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.指情報.指種類
 * @author chungnt
 *
 */
@AllArgsConstructor
public enum FingerType {
	
	/**
	 * 人差し指
	 */
	INDEXFINGER(0, "人差し指"),
	
	/**
	 * 	中指
	 */
	MIDDLEFINGER(1, "中指");
	
	public final int value;

	public final String name;
	
	private final static FingerType[] values = FingerType.values();

	public static FingerType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FingerType val : FingerType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
