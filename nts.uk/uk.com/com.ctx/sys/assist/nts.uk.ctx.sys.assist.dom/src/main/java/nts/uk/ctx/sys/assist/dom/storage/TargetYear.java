package nts.uk.ctx.sys.assist.dom.storage;

import lombok.Getter;

/**
 * 対象年
 */
@Getter
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
	
	private final int value;
	private final String nameId;
	
	private TargetYear(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
