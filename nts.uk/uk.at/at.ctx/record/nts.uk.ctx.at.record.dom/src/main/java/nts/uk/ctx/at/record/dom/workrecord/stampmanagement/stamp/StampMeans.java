package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.AllArgsConstructor;

/**
 * @author ThanhNX
 *
 *         打刻手段
 */
@AllArgsConstructor
public enum StampMeans {

	// 0:氏名選択
	NAME_SELECTION(0, "氏名選択"),

	// 1:指認証打刻
	FINGER_AUTHC(1, "指認証打刻"),

	// 2:ICカード打刻
	IC_CARD(2, "ICカード打刻"),

	// 3:個人打刻
	INDIVITION(3, "個人打刻"),

	// 4:ポータル打刻
	PORTAL(4, "ポータル打刻"),

	// 5:スマホ打刻
	SMART_PHONE(5, "スマホ打刻"),

	// 6:タイムレコーダー打刻
	TIME_CLOCK(6, "タイムレコーダー打刻"),

	// 7:テキスト受入
	TEXT(7, "テキスト受入"),

	// 8:リコー複写機打刻
	RICOH_COPIER(8, "リコー複写機打刻");

	public final int value;

	public final String name;

	/** The Constant values. */
	private final static StampMeans[] values = StampMeans.values();

	public static StampMeans valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (StampMeans val : StampMeans.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
