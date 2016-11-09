package nts.uk.ctx.pr.proto.dom.enums;

/**
 * 
 * 合計対象区分
 *
 */
public enum SumScopeAtr {
	// 0:対象外
	EXCLUDED(0),
	// 1:対象内
	INCLUDED(1);
	public final int value;

	/** return 値 */
	public int value() {
		return value;
	}

	/**
	 * Constructor
	 * 
	 * @param 合計対象区分
	 */
	private SumScopeAtr(int value) {
		this.value = value;
	}
	/**
	 * 
	 * @param 合計対象区分の値
	 * @return　合計対象区分
	 */
	public static SumScopeAtr valueOf(int value) {
		switch (value) {
		case 0:
			return EXCLUDED;
		case 1:
			return INCLUDED;
		default:
			throw new RuntimeException("Invalid value of SumScopeAtr");
		}
	}
}
