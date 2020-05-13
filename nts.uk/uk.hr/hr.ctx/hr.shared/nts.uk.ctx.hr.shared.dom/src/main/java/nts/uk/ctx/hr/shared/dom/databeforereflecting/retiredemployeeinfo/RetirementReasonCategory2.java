package nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo;

//退職理由区分2
public enum RetirementReasonCategory2 {
	// 結婚
	Marriage(1, "結婚"),
	// 上司と合わない
	NotMatchBoss(2, "上司と合わない"),
	// やる気がなくなった
	Motivated(3, "やる気がなくなった"),
	// 会社の業績不振
	CompanyPerformanceDown(4, "会社の業績不振"),
	// その他
	Other(5, "その他");

	public final int value;

	public final String name;

	private RetirementReasonCategory2(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
