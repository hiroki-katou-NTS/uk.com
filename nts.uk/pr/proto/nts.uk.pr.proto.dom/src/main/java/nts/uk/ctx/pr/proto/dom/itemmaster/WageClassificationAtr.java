package nts.uk.ctx.pr.proto.dom.itemmaster;
/** 賃金対象区分 */
public enum WageClassificationAtr {
	//0:対象外
	UN_SUBJECT(0),
	//1:対象
	SUBJECT(1);
	
	public final int value;

	/** 値 */
	public int value() {
		return value;
	}
	/**
	 * Constructor.
	 * 
	 * @param 賃金対象区分の値
	 */
	private WageClassificationAtr(int value) {
		this.value = value;
	}
	/**
	 * 
	 * @param value
	 * @return 賃金対象区分
	 */
	public WageClassificationAtr valueOf(int value)
	{
		switch (value) {
		case 0:
			return UN_SUBJECT;
		case 1:
			return SUBJECT;
		default:
			throw new RuntimeException("Invalid value of WageClassificationAtr");
		}
	}

}
