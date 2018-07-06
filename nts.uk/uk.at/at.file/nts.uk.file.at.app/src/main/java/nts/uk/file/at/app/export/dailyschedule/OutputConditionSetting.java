package nts.uk.file.at.app.export.dailyschedule;

/**
 * Output condition setting
 * @author HoangNDH
 *
 */
public enum OutputConditionSetting {
	USE_CONDITION(1),
	
	NOT_USE_CONDITION(0);
	
	private final int outputSetting;

	private OutputConditionSetting(int outputSetting) {
		this.outputSetting = outputSetting;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static OutputConditionSetting valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OutputConditionSetting val : OutputConditionSetting.values()) {
			if (val.outputSetting == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
