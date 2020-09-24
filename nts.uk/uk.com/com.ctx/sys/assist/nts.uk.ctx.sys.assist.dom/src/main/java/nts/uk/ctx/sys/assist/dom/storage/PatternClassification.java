package nts.uk.ctx.sys.assist.dom.storage;

/**
 * パターン区分
 */
public enum PatternClassification {
	/**
	 * ユーザー任意
	 */
	USER_OPTIONAL(0, "Enum_PatternClassfication_USER_OPTIONAL"),
	/**
	 * 日通で用意したテンプレート
	 */
	NE_PREPARED_TEMPLATE(1, "Enum_PatternClassfication_NE_PREPARED_TEMPLATE");
	
	public final int value;
	public final String nameId;
	
	private PatternClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
