package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum NumberOfTimeOverLimitType {
	// 0: 0‰ñ
	ZERO_TIMES(0),
	// 1: 1‰ñ
	ONCE(1),
	// 2: 2‰ñ
	TWICE(2),
	// 3: 3‰ñ
	THREE_TIMES(3),
	// 4: 4‰ñ
	FOUR_TIMES(4),
	// 5: 5‰ñ
	FIVE_TIMES(5),
	// 6: 6‰ñ
	SIX_TIMES(6),
	// 7: 7‰ñ
	SEVEN_TIMES(7),
	// 8: 8‰ñ
	EIGHT_TIMES(8),
	// 9: 9‰ñ
	NINE_TIMES(9),
	// 10: 10‰ñ
	TEN_TIMES(10),
	// 11: 11‰ñ
	ELEVEN_TIMES(11),
	// 12: 12‰ñ
	TWELVE_TIMES(12);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "0‰ñ";
			break;
		case 1:
			name = "1‰ñ";
			break;
		case 2:
			name = "2‰ñ";
			break;
		case 3:
			name = "3‰ñ";
			break;
		case 4:
			name = "4‰ñ";
			break;
		case 5:
			name = "5‰ñ";
			break;
		case 6:
			name = "6‰ñ";
			break;
		case 7:
			name = "7‰ñ";
			break;
		case 8:
			name = "8‰ñ";
			break;
		case 9:
			name = "9‰ñ";
			break;
		case 10:
			name = "10‰ñ";
			break;
		case 11:
			name = "11‰ñ";
			break;
		case 12:
			name = "12‰ñ";
			break;
		default:
			name = "6‰ñ";
			break;
		}
		return name;
	}
}
