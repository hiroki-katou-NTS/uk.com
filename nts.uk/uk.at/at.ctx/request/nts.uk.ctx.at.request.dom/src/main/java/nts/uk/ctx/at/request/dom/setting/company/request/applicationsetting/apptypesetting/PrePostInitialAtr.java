package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

/**
 * 事前事後区分初期値
 * @author Doan Duy Hung
 *
 */

public enum PrePostInitialAtr {

	/**
	 * 事前
	 */
	PRE(0, "事前"),

	/**
	 * 事後
	 */
	POST(1, "事後"),

	/**
	 * 選択なし
	 */
	NO_CHOISE(2, "選択なし ");

	public final Integer value;

	public final String name;

	private PrePostInitialAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}

}
