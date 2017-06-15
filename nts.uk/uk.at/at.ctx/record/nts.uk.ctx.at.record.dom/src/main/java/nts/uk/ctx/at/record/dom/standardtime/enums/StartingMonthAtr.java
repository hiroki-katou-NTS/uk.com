package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum StartingMonthAtr {

	// 0: 1ŒŽ
	JANUARY(0),
	// 1: 2ŒŽ
	FEBRUARY(1),
	// 2: 3ŒŽ
	MARCH(2),
	// 3: 4ŒŽ
	APRIL(3),
	// 4: 5ŒŽ
	MAY(4),
	// 5: 6ŒŽ
	JUNE(5),
	// 6: 7ŒŽ
	JULY(6),
	// 7: 8ŒŽ
	AUGUST(7),
	// 8: 9ŒŽ
	SEPTEMBER(8),
	// 9: 10ŒŽ
	OCTOBER(9),
	// 10: 11ŒŽ
	NOVEMBER(10),
	// 11: 12ŒŽ
	DECEMBER(11);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "1ŒŽ";
			break;
		case 1:
			name = "2ŒŽ";
			break;
		case 2:
			name = "3ŒŽ";
			break;
		case 3:
			name = "4ŒŽ";
			break;
		case 4:
			name = "5ŒŽ";
			break;
		case 5:
			name = "6ŒŽ";
			break;
		case 6:
			name = "7ŒŽ";
			break;
		case 7:
			name = "8ŒŽ";
			break;
		case 8:
			name = "9ŒŽ";
			break;
		case 9:
			name = "10ŒŽ";
			break;
		case 10:
			name = "11ŒŽ";
			break;
		case 11:
			name = "12ŒŽ";
			break;
		default:
			name = "1ŒŽ";
			break;
		}
		return name;
	}

}
