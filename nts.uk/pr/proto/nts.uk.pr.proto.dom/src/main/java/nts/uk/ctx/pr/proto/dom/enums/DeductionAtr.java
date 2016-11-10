package nts.uk.ctx.pr.proto.dom.enums;
/**控除項目種類*/
public enum DeductionAtr {
	//0:任意控除
	ANY_DEDUCTION(0),
	//1:社保控除
	SOCIAL_INSURANCE_DEDUCTION(1),
	//2:所得税控除
	INCOME_TAX_CREDIT(2),
	//3:住民税控除
	RESIDENT_TAX_DEDUCTION(3);
	
	public final int value;

	/** 値 */
	public int value() {
		return value;
	}
	/**
	 * Constructor.
	 * 
	 * @param 控除項目種類
	 */
	private DeductionAtr(int value) {
		this.value = value;
	}
	
	public DeductionAtr valueOf(int value){
		switch (value) {
		case 0:
			return ANY_DEDUCTION;
		case 1:
			return SOCIAL_INSURANCE_DEDUCTION;
		case 2:
			return INCOME_TAX_CREDIT;
		case 3:
			return RESIDENT_TAX_DEDUCTION;
		default:
			throw new RuntimeException("Invalid value of AvgPaidAtr");
		}
	}
	
}
