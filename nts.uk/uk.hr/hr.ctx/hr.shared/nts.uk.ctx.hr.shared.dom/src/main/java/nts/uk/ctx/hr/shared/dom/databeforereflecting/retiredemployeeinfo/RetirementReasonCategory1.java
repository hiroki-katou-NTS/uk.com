package nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo;

//退職理由区分１
public enum RetirementReasonCategory1 {
	// 自己都合による退職
	RetirementForPersonal(1, "自己都合による退職"),
	// 定年による退職
	RetirementDueToRetirement(2, "定年による退職"),
	// 会社都合による解雇
	RetirementDueToCompany(3, "会社都合による解雇");
	public final int value;

	public final String name;

	private RetirementReasonCategory1(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
