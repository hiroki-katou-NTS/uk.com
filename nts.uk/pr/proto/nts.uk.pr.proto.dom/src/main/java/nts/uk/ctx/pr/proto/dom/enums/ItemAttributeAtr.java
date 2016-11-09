package nts.uk.ctx.pr.proto.dom.enums;
/** 項目属性 */
public enum ItemAttributeAtr {
	//0:時間
	HOURS(0),
	//1:回数
	TIMES(1),	
	//2:金額（小数点無し）
	AMOUNT_NO_DECIMAL(2),
	//3:金額（小数点あり）
	AMOUNT_WITH_DECIMAL(3),
	//4:文字
	CHARACTERS(4);
	
	public final int value;

	/** 値 */
	public int value() {
		return value;
	}
	/**
	 * Constructor.
	 * 
	 * @param 項目属性 
	 */
	private ItemAttributeAtr(int value) {
		this.value = value;
	}
	
	public ItemAttributeAtr valueOf(int value)	{
		switch (value) {
		case 0:
			return HOURS;
		case 1:
			return TIMES;
		case 2:
			return AMOUNT_NO_DECIMAL;
		case 3:
			return AMOUNT_WITH_DECIMAL;
		case 4:
			return CHARACTERS;
		default:
			throw new RuntimeException("Invalid value of ItemAttributeAtr");
		}
	}
}
