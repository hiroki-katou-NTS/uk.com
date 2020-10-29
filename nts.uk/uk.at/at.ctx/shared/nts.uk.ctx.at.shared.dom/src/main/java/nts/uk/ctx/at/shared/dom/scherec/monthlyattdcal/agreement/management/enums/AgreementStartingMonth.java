package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums;

/**
 * ３６協定起算月
 * @author nampt
 *
 */
public enum AgreementStartingMonth {

	// 0: 1月
	JANUARY(1),
	// 1: 2月
	FEBRUARY(2),
	// 2: 3月
	MARCH(3),
	// 3: 4月
	APRIL(4),
	// 4: 5月
	MAY(5),
	// 5: 6月
	JUNE(6),
	// 6: 7月
	JULY(7),
	// 7: 8月
	AUGUST(8),
	// 8: 9月
	SEPTEMBER(9),
	// 9: 10月
	OCTOBER(10),
	// 10: 11月
	NOVEMBER(11),
	// 11: 12月
	DECEMBER(12);

	public final int value;

	private AgreementStartingMonth(int type) {
		this.value = type;
	}

	public int getMonth() {
		return this.value + 1;
	}

}
