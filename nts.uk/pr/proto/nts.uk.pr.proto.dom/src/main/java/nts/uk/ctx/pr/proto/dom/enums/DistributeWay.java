package nts.uk.ctx.pr.proto.dom.enums;

public enum DistributeWay {
	//0:割合で計算
	CALCULATED_PERCENTAGE(0),
	//1:日数控除
	DEDUCTION_FOR_DAYS(1),
	//2:計算式
	CALCULATION_FORMULA(2);
	public final int value;

	/**
	 * 
	 * 値
	 */
	public int value() {
		return value;
	}
	/**
	 * Constructor.
	 * 
	 * @param カテゴリ区分の値
	 */
	private DistributeWay(int value) {
		this.value = value;
	}
	public DistributeWay valueOf(int value){
		switch (value) {
		case 0:
			return CALCULATED_PERCENTAGE;
		case 1:
			return DEDUCTION_FOR_DAYS;
		case 2:
			return CALCULATION_FORMULA;
		default:
			throw new RuntimeException("Invalid value of DistributeWay");
		}
	}
	
}
