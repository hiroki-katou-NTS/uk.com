package nts.uk.ctx.pr.proto.dom.enums;

public enum DistributeSet {
	//0:按分しない
	NOT_PROPORTIONAL(0),
	//1:按分する
	PROPORTIONAL(1),
	//2:月1回支給
	MONTHLY_PAYMENT(2);
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
	private DistributeSet(int value) {
		this.value = value;
	}
	public DistributeSet valueOf(int value){
		switch (value) {
		case 0:
			return NOT_PROPORTIONAL;
		case 1: 
			return PROPORTIONAL;
		case 2:
			return MONTHLY_PAYMENT;

		default:
			throw new RuntimeException("Invalid value of CategoryAtr");
		}
	}
	
}
