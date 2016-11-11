package nts.uk.ctx.pr.proto.dom.layout.detail;

public enum DistributeWay {
	// 0:割合で計算
	CALCULATED_PERCENTAGE(0),
	// 1:日数控除
	DEDUCTION_FOR_DAYS(1),
	// 2:計算式
	CALCULATION_FORMULA(2);
	public final int value;

	/**
	 * Constructor.
	 * 
	 * @param カテゴリ区分の値
	 */
	DistributeWay(int value) {
		this.value = value;
	}
}
