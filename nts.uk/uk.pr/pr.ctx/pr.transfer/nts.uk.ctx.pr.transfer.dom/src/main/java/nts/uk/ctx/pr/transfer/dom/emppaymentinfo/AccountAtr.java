package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author HungTT - 口座区分
 *
 */
public enum AccountAtr {

	/**
	 * 普通
	 */
	USUALLY(0, "普通"),
	/**
	 * 当座
	 */
	CURRENT(1, "当座");
	public final int value;
	public final String nameId;

	private AccountAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static AccountAtr of(int value) {
		return EnumAdaptor.valueOf(value, AccountAtr.class);
	}

}
