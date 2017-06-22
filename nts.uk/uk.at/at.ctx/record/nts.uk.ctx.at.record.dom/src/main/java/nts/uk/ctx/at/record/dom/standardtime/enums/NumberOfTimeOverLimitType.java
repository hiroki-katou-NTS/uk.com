package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum NumberOfTimeOverLimitType {
	
	/*
	 * ３６協定超過上限回数
	 */
	// 0: 0回
	ZERO_TIMES(0),
	// 1: 1回
	ONCE(1),
	// 2: 2回
	TWICE(2),
	// 3: 3回
	THREE_TIMES(3),
	// 4: 4回
	FOUR_TIMES(4),
	// 5: 5回
	FIVE_TIMES(5),
	// 6: 6回
	SIX_TIMES(6),
	// 7: 7回
	SEVEN_TIMES(7),
	// 8: 8回
	EIGHT_TIMES(8),
	// 9: 9回
	NINE_TIMES(9),
	// 10: 10回
	TEN_TIMES(10),
	// 11: 11回
	ELEVEN_TIMES(11),
	// 12: 12回
	TWELVE_TIMES(12);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "0回";
			break;
		case 1:
			name = "1回";
			break;
		case 2:
			name = "2回";
			break;
		case 3:
			name = "3回";
			break;
		case 4:
			name = "4回";
			break;
		case 5:
			name = "5回";
			break;
		case 6:
			name = "6回";
			break;
		case 7:
			name = "7回";
			break;
		case 8:
			name = "8回";
			break;
		case 9:
			name = "9回";
			break;
		case 10:
			name = "10回";
			break;
		case 11:
			name = "11回";
			break;
		case 12:
			name = "12回";
			break;
		default:
			name = "6回";
			break;
		}
		return name;
	}
}
