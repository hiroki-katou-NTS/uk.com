package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum ClosingDateType {
	
	/*
	 * ３６協定締め日
	 */
	// 0: 1日
	FIRST(0),
	// 1: 2日
	SECOND(1),
	// 2: 3日
	THIRD(2),
	// 3: 4日
	FOURTH(3),
	// 4: 5日
	FIFTH(4),
	// 5: 6日
	SIXTH(5),
	// 6: 7日
	SEVENTH(6),
	// 7: 8日
	EIGHTH(7),
	// 8: 9日
	NINTH(8),
	// 9: 10日
	TENTH(9),
	// 10: 11日
	ELEVENTH(10),
	// 11: 12日
	TWELFTH(11),
	// 12: 13日
	THIRTEENTH(12),
	// 13: 14日
	FOURTHTEENTH(13),
	// 14: 15日
	FIFTEENTH(14),
	// 15: 16日
	SIXTEENTH(15),
	// 16: 17日
	SEVENTEENTH(16),
	// 17: 18日
	EIGHTEENTH(17),
	// 18: 19日
	NINETEENTH(18),
	// 19: 20日
	TWENTIETH(19),
	// 20: 21日
	TWENTY_FIRST(20),
	// 21: 22日
	TWENTY_SECOND(21),
	// 22: 23日
	TWENTY_THIRD(22),
	// 23: 24日
	TWENTY_FOURTH(23),
	// 24: 25日
	TWENTY_FIFTH(24),
	// 25: 26日
	TWENTY_SIXTH(25),
	// 26: 27日
	TWENTY_SEVENTH(26),
	// 27: 28日
	TWENTY_EIGHTH(27),
	// 28: 29日
	TWENTY_NINTH(28),
	// 29: 30日
	THIRTIETH(29),
	// 30: 末日
	LASTDAY(30);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "1日";
			break;
		case 1:
			name = "2日";
			break;
		case 2:
			name = "3日";
			break;
		case 3:
			name = "4日";
			break;
		case 4:
			name = "5日";
			break;
		case 5:
			name = "6日";
			break;
		case 6:
			name = "7日";
			break;
		case 7:
			name = "8日";
			break;
		case 8:
			name = "9日";
			break;
		case 9:
			name = "10日";
			break;
		case 10:
			name = "11日";
			break;
		case 11:
			name = "12日";
			break;
		case 12:
			name = "13日";
			break;
		case 13:
			name = "14日";
			break;
		case 14:
			name = "15日";
			break;
		case 15:
			name = "16日";
			break;
		case 16:
			name = "17日";
			break;
		case 17:
			name = "18日";
			break;
		case 18:
			name = "19日";
			break;
		case 19:
			name = "20日";
			break;
		case 20:
			name = "21日";
			break;
		case 21:
			name = "22日";
			break;
		case 22:
			name = "23日";
			break;
		case 23:
			name = "24日";
			break;
		case 24:
			name = "25日";
			break;
		case 25:
			name = "26日";
			break;
		case 26:
			name = "27日";
			break;
		case 27:
			name = "28日";
			break;
		case 28:
			name = "29日";
			break;
		case 29:
			name = "30日";
			break;
		case 30:
			name = "末日";
			break;
		default:
			name = "1日";
			break;
		}
		return name;
	}
}
