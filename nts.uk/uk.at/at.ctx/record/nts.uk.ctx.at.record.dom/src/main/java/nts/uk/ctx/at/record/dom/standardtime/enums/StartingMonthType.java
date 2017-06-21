package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum StartingMonthType {

	/*
	 * ３６協定起算月
	 */
	// 0: 1月
	JANUARY(0),
	// 1: 2月
	FEBRUARY(1),
	// 2: 3月
	MARCH(2),
	// 3: 4月
	APRIL(3),
	// 4: 5月
	MAY(4),
	// 5: 6月
	JUNE(5),
	// 6: 7月
	JULY(6),
	// 7: 8月
	AUGUST(7),
	// 8: 9月
	SEPTEMBER(8),
	// 9: 10月
	OCTOBER(9),
	// 10: 11月
	NOVEMBER(10),
	// 11: 12月
	DECEMBER(11);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "1月";
			break;
		case 1:
			name = "2月";
			break;
		case 2:
			name = "3月";
			break;
		case 3:
			name = "4月";
			break;
		case 4:
			name = "5月";
			break;
		case 5:
			name = "6月";
			break;
		case 6:
			name = "7月";
			break;
		case 7:
			name = "8月";
			break;
		case 8:
			name = "9月";
			break;
		case 9:
			name = "10月";
			break;
		case 10:
			name = "11月";
			break;
		case 11:
			name = "12月";
			break;
		default:
			name = "1月";
			break;
		}
		return name;
	}

}
