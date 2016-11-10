package nts.uk.ctx.pr.proto.dom.enums;
/** 課税区分 */
public enum TaxAtr {
	//0:課税
	TAXATION(0),
	//1:非課税（限度あり）
	TAX_FREE_LIMIT(1),
	//2:非課税（限度無し）
	TAX_FREE_UN_LIMIT(2),
	//3:通勤費（手入力）
	COMMUTING_COST(3),
	//4:通勤費（定期券利用）
	COMMUTING_EXPENSE(4);
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
	private TaxAtr(int value) {
		this.value = value;
	}
	
	public TaxAtr valueOf(int value){
		switch (value) {
		case 0:
			return TAXATION;
		case 1:
			return TAX_FREE_LIMIT;
		case 2:
			return TAX_FREE_UN_LIMIT;
		case 3: 
			return COMMUTING_COST;
		case 4:
			return COMMUTING_EXPENSE;
		default:
			throw new RuntimeException("Invalid value of TaxAtr");
		}
	}
}
