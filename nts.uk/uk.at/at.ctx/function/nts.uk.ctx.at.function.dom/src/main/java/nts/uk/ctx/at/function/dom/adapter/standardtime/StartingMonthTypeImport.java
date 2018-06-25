package nts.uk.ctx.at.function.dom.adapter.standardtime;

/**
 * @author dat.lh
 *
 */
public enum StartingMonthTypeImport {
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
	
	private StartingMonthTypeImport(int type) {
		this.value = type;
	}

}
