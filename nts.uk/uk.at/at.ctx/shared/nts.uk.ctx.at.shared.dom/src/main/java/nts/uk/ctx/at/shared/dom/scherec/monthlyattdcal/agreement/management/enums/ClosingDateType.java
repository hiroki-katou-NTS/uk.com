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
	// 1: 1日
	FIRST(1),
	// 2: 2日
	SECOND(2),
	// 3: 3日
	THIRD(3),
	// 4: 4日
	FOURTH(4),
	// 5: 5日
	FIFTH(5),
	// 6: 6日
	SIXTH(6),
	// 7: 7日
	SEVENTH(7),
	// 8: 8日
	EIGHTH(8),
	// 9: 9日
	NINETH(9),
	// 10: 10日
	TENTH(10),
	// 11: 11日
	ELEVENTH(11),
	// 12: 12日
	TWELFTH(12),
	// 13: 13日
	THIRTEENTH(13),
	// 14: 14日
	FOURTEENTH(14),
	// 15: 15日
	FIFTEENTH(15),
	// 16: 16日
	SIXTEENTH(16),
	// 17: 17日
	SEVENTEENTH(17),
	// 18: 18日
	EIGHTEENTH(18),
	// 19: 19日
	NINETEENTH(19),
	// 20: 20日
	TWENTIETH(20),
	// 21: 21日
	TWENTY_FIRST(21),
	// 22: 22日
	TWENTY_SECOND(22),
	// 23: 23日
	TWENTY_THIRD(23),
	// 24: 24日
	TWENTY_FOURTH(24),
	// 25: 25日
	TWENTY_FIFTH(25),
	// 26: 26日
	TWENTY_SIXTH(26),
	// 27: 27日
	TWENTY_SEVENTH(27),
	// 28: 28日
	TWENTY_EIGHTH(28),
	// 29: 29日
	TWENTY_NINTH(29),
	// 30: 30日
	THIRTIETH(30),
	// 31: 末日
	LASTDAY(31);

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
