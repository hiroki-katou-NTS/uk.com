package nts.uk.ctx.pr.proto.dom.enums;

/** 高年齢継続被保険者区分 */
public enum AgeContinuationInsureAtr {
	// 0:非対象
	NOT_TARGET(0),
	// 1:対象
	TARGET(1);

	public final int value;

	/**
	 * Constructor.
	 * 
	 * @param 高年齢継続被保険者区分
	 */
	private AgeContinuationInsureAtr(int value) {
		this.value = value;
	}
}
