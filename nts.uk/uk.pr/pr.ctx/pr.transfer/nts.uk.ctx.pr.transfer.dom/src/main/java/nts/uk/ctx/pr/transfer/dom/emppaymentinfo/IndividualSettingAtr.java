package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

/**
 * 
 * @author HungTT - 個別設定区分
 *
 */
public enum IndividualSettingAtr {

	/**
	 * 給与と同一設定で支払う
	 */
	PAY_WITH_SAME_SALARY_SETTING(false, "給与と同一設定で支払う"),
	/**
	 * 個別の設定で支払う
	 */
	PAY_WITH_INDIVIDUAL_SETTING(true, "個別の設定で支払う");

	/** The value. */
	public final boolean value;

	/** The name id. */
	public final String nameId;

	private IndividualSettingAtr(boolean value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static IndividualSettingAtr of(boolean value) {
		if (value)
			return IndividualSettingAtr.PAY_WITH_INDIVIDUAL_SETTING;
		else
			return IndividualSettingAtr.PAY_WITH_SAME_SALARY_SETTING;
	}

}
