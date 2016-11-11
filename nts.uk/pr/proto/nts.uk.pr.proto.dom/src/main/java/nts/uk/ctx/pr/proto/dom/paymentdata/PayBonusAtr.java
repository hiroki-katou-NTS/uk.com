package nts.uk.ctx.pr.proto.dom.paymentdata;

/**
 * ���^�ܗ^�敪
 * 
 * @author vunv
 *
 */
public enum PayBonusAtr {
	/**
	 * ���^
	 */
	SALARY(0),

	/**
	 * �ܗ^
	 */
	BONUSES(1);

	public int value;

	private PayBonusAtr(int value) {
		this.value = value;
	}

}
