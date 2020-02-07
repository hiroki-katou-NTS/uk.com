package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums;

/**
 * @author thanhpv
 * 定年退職コース区分
 */
public enum RetirePlanCourseClass {

	STANDARD_COURSE (0,"標準コース"),
	
	EARLY_RETIREMENT_COURSE (1, "早期退職コース");
	
	public int value;
	
	public String nameId;

	RetirePlanCourseClass(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
