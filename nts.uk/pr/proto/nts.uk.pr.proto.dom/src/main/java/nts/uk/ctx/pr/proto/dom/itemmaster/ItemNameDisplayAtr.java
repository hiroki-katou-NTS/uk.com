package nts.uk.ctx.pr.proto.dom.itemmaster;

/** 項目名表示区分 */
public enum ItemNameDisplayAtr {
	// 0:ゼロ円の場合明細書から項目名も出さない
	NOT_PUT_IF_ZERO(0),
	// 1:出す
	PUT_ALWAYS(1);
	public final int value;

	/** 値 */
	public int value() {
		return value;
	}

	/**
	 * Constructor.
	 * 
	 * @param 項目名表示区分
	 */
	private ItemNameDisplayAtr(int value) {
		this.value = value;
	}
}
