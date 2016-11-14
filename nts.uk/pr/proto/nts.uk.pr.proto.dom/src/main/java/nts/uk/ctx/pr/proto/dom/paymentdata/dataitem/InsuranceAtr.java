package nts.uk.ctx.pr.proto.dom.paymentdata.dataitem;

/**
 * 保対象区分
 * 
 * @author vunv
 *
 */
public enum InsuranceAtr {
	/**
	 * 0:対象外
	 */
	UN_SUBJECT(0),

	/**
	 * 1:対象
	 */
	SUBJECT(1);

	public int value;

	private InsuranceAtr(int value) {
		this.value = value;
	}

}
