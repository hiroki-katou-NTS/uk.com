package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums;

/**
 * 
 * @author nampt
 *
 */

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
	NINETH(8),
	// 9: 10日
	TENTH(9),
	// 10: 11日
	ELEVENTH(10),
	// 11: 12日
	TWELFTH(11),
	// 12: 13日
	THIRTEENTH(12),
	// 13: 14日
	FOURTEENTH(13),
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
	
	private ClosingDateType(int type) {
		this.value = type;
	}

	/**
	 * 末日であるか判定する
	 * @return　末日である
	 */
	public boolean isLastDay() {
		return this.equals(LASTDAY);
	}

}
