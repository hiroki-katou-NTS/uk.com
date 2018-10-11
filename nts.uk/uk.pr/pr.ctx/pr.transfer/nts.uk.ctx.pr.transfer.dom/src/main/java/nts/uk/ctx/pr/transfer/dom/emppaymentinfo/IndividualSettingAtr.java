package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author HungTT - 個別設定区分
 *
 */
public enum IndividualSettingAtr {

	/**
	 * 給与と同一設定で支払う
	 */
	PAY_WITH_SAME_SALARY_SETTING(0, "給与と同一設定で支払う"),
	/**
	 * 個別の設定で支払う
	 */
	PAY_WITH_INDIVIDUAL_SETTING(1, "個別の設定で支払う");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private IndividualSettingAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static IndividualSettingAtr of(int value) {
		return EnumAdaptor.valueOf(value, IndividualSettingAtr.class);
	}

}
