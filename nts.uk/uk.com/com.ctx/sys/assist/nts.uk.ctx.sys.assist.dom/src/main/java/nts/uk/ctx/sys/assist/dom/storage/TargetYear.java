package nts.uk.ctx.sys.assist.dom.storage;

/**
 * 対象年
 */
public enum TargetYear {
	/**
	 * 当年
	 */
	CURRENT_YEAR(1, "Enum_TargetYear_CURRENT_YEAR"),
	/**
	 * 前年
	 */
	PREVIOUS_YEAR(2, "Enum_TargetYear_PREVIOUS_YEAR"),
	/**
	 * 前々年
	 */
	TWO_YEARS_AGO(3, "Enum_TargetYear_TWO_YEARS_AGO");
	
	public final int value;
	public final String nameId;
	
	private TargetYear(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
