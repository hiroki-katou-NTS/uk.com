package nts.uk.ctx.pr.core.dom.vacationsetting.acquisitionrule;

import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;

// TODO: Auto-generated Javadoc
/**
 * The Enum Settingclassification.
 */
public enum Settingclassification {
	
	/** The Setting. */
	Setting(1, "設定する"),
	
	/** The No setting. */
	NoSetting(0, "設定しない");
	
	/** The value. */
	public int value;

	/** The description. */
	public String description;
	
	/** The Constant values. */
	private final static Settingclassification[] values = Settingclassification.values();
	
	/**
	 * Instantiates a new settingclassification.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private Settingclassification(int value, String description) {
		this.value = value;
		this.description = description;
	}
}
