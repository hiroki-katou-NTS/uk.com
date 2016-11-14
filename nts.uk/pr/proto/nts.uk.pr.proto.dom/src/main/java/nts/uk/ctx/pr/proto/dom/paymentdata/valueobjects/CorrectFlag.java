package nts.uk.ctx.pr.proto.dom.paymentdata.valueobjects;

/**
 * 修正フラグ
 * 
 * @author vunv
 *
 */
public enum CorrectFlag {
	/**
	 * 0:修正なし
	 */
	NO_MODIFY(0),

	/**
	 * 1:修正あり
	 */
	MODIFY(1);

	public int value;

	private CorrectFlag(int value) {
		this.value = value;
	}

}
