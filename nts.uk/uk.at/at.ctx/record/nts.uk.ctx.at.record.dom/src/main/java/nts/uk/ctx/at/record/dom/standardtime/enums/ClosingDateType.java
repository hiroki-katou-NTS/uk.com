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
	 * ‚R‚U‹¦’è’÷‚ß“ú
	 */
	// 0: 1“ú
	FIRST(0),
	// 1: 2“ú
	SECOND(1),
	// 2: 3“ú
	THIRD(2),
	// 3: 4“ú
	FOURTH(3),
	// 4: 5“ú
	FIFTH(4),
	// 5: 6“ú
	SIXTH(5),
	// 6: 7“ú
	SEVENTH(6),
	// 7: 8“ú
	EIGHTH(7),
	// 8: 9“ú
	NINTH(8),
	// 9: 10“ú
	TENTH(9),
	// 10: 11“ú
	ELEVENTH(10),
	// 11: 12“ú
	TWELFTH(11),
	// 12: 13“ú
	THIRTEENTH(12),
	// 13: 14“ú
	FOURTHTEENTH(13),
	// 14: 15“ú
	FIFTEENTH(14),
	// 15: 16“ú
	SIXTEENTH(15),
	// 16: 17“ú
	SEVENTEENTH(16),
	// 17: 18“ú
	EIGHTEENTH(17),
	// 18: 19“ú
	NINETEENTH(18),
	// 19: 20“ú
	TWENTIETH(19),
	// 20: 21“ú
	TWENTY_FIRST(20),
	// 21: 22“ú
	TWENTY_SECOND(21),
	// 22: 23“ú
	TWENTY_THIRD(22),
	// 23: 24“ú
	TWENTY_FOURTH(23),
	// 24: 25“ú
	TWENTY_FIFTH(24),
	// 25: 26“ú
	TWENTY_SIXTH(25),
	// 26: 27“ú
	TWENTY_SEVENTH(26),
	// 27: 28“ú
	TWENTY_EIGHTH(27),
	// 28: 29“ú
	TWENTY_NINTH(28),
	// 29: 30“ú
	THIRTIETH(29),
	// 30: ––“ú
	LASTDAY(30);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "1“ú";
			break;
		case 1:
			name = "2“ú";
			break;
		case 2:
			name = "3“ú";
			break;
		case 3:
			name = "4“ú";
			break;
		case 4:
			name = "5“ú";
			break;
		case 5:
			name = "6“ú";
			break;
		case 6:
			name = "7“ú";
			break;
		case 7:
			name = "8“ú";
			break;
		case 8:
			name = "9“ú";
			break;
		case 9:
			name = "10“ú";
			break;
		case 10:
			name = "11“ú";
			break;
		case 11:
			name = "12“ú";
			break;
		case 12:
			name = "13“ú";
			break;
		case 13:
			name = "14“ú";
			break;
		case 14:
			name = "15“ú";
			break;
		case 15:
			name = "16“ú";
			break;
		case 16:
			name = "17“ú";
			break;
		case 17:
			name = "18“ú";
			break;
		case 18:
			name = "19“ú";
			break;
		case 19:
			name = "20“ú";
			break;
		case 20:
			name = "21“ú";
			break;
		case 21:
			name = "22“ú";
			break;
		case 22:
			name = "23“ú";
			break;
		case 23:
			name = "24“ú";
			break;
		case 24:
			name = "25“ú";
			break;
		case 25:
			name = "26“ú";
			break;
		case 26:
			name = "27“ú";
			break;
		case 27:
			name = "28“ú";
			break;
		case 28:
			name = "29“ú";
			break;
		case 29:
			name = "30“ú";
			break;
		case 30:
			name = "––“ú";
			break;
		default:
			name = "1“ú";
			break;
		}
		return name;
	}
}
