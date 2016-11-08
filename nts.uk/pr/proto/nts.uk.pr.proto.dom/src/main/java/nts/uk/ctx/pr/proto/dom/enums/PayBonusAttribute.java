package nts.uk.ctx.pr.proto.dom.enums;

/**
 * ‹‹—^Ü—^‹æ•ª
 * 
 * @author vunv
 *
 */
public enum PayBonusAttribute {
	/**
	 * ‹‹—^
	 */
	SALARY(0),

	/**
	 * Ü—^
	 */
	BONUSES(1);

	public int value;

	private PayBonusAttribute(int value) {
		this.value = value;
	}

}
