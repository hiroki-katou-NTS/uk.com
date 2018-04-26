package nts.uk.ctx.sys.assist.dom.system;

import nts.uk.shr.com.i18n.TextResource;

public enum SystemTypeEnum {
	PERSON_SYSTEM(0, TextResource.localize("CMF005_170")),
	ATTENDANCE_SYSTEM(1, TextResource.localize("CMF005_171")),
	PAYROLL_SYSTEM(2, TextResource.localize("CMF005_172")),
	OFFICE_HELPER(3, TextResource.localize("CMF005_173"));
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SystemTypeEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
