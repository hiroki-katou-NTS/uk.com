package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;
/**
 * 
 * @author dudt
 *
 */
@AllArgsConstructor
public enum StampCombinationAtr {
	// なし
	NONE(0, "なし"),
	// フレックス
	FLEX(1, "フレックス"),
	// 半休
	HALFHOLIDAY(2, "半休"),
	// 残業
	OVERTIME(3, "残業"),
	// 休出
	LEAVE(4, "休出"),
	// 早出
	EARLY(5, "早出"),
	// 直行
	DIRECT(6, "直行"),
	// 直帰
	BOUNCE(7, "直帰");

	public final int value;
	public final String name;

	public String toName() {
		String name;
		switch (value) {
		case 1:
			name = "フレックス";
			break;
		case 2:
			name = "半休";
			break;
		case 3:
			name = "残業";
			break;
		case 4:
			name = "休出";
			break;
		case 5:
			name = "早出";
			break;
		case 6:
			name = "直行";
			break;
		case 7:
			name = "直帰";
			break;
		default:
			name = "なし";
			break;
		}
		return name;
	}
}
