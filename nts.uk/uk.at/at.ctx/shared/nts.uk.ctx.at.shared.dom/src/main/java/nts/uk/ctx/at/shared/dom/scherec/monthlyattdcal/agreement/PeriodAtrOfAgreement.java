package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

/**
 * 期間区分
 * @author shuichu_ishida
 */
public enum PeriodAtrOfAgreement {
	/** 2ヶ月 */
	TWO_MONTHS(0),
	/** 3ヶ月 */
	THREE_MONTHS(1),
	/** 1ヶ月 */
	ONE_MONTH(2),
	/** 1年間 */
	ONE_YEAR(3),
	/** 複数月平均 */
	Months_Average(4);
	
	public int value;
	private PeriodAtrOfAgreement(int value){
		this.value = value;
	}
}
