package nts.uk.ctx.sys.assist.dom.system;

import nts.uk.shr.com.i18n.TextResource;

public enum SystemTypeEnum {
	HUMAN_RES_OFFICER_SYSTEM(0, TextResource.localize("CMF005_170")),//人事システム 
	EMPLOYMENT_SYSTEM(1, TextResource.localize("CMF005_171")),//就業システム
	SALARY_PROFESSIONAL_SYSTEM(2, TextResource.localize("CMF005_172")),//給与システム
	OFFICE_HELPER_SYSTEM(3, TextResource.localize("CMF005_173"));//オフィスヘルパー
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String name;

	private SystemTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/** The Constant values. */
	private final static SystemTypeEnum[] values = SystemTypeEnum.values();
	
	/**
	 * Get SystemTypeEnum bye value
	 *
	 * @param value of enum
	 * @return SystemTypeEnum
	 */
	public static SystemTypeEnum valueOf(int value) {
		
		// Find value.
		for (SystemTypeEnum val : SystemTypeEnum.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
