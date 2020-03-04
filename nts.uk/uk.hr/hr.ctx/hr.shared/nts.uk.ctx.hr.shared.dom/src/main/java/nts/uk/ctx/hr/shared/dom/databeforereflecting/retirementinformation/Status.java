package nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
	// 未登録
	Unregistered(0,""),
	// 登録済み/承認待ち
	// Registered / Approval Pending
	Registered(1,"承認待ち"),
	// Approved / Waiting for Reflection
	// 承認済み/反映待ち
	Approved(2,"反映待ち"),
	// 反映済み
	Reflected(3,"反映済み"),
	// 否認/差戻し
	Deny(4,"差戻し");
	public final int value;
	public final String name;
}
