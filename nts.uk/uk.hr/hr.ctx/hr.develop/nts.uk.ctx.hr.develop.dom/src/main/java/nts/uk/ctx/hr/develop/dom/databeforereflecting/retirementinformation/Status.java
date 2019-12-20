package nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
	// 未登録
	Unregistered(0),
	// 登録済み/承認待ち
	// Registered / Approval Pending
	Registered(1),
	// Approved / Waiting for Reflection
	// 承認済み/反映待ち
	Approved(2),
	// 反映済み
	Reflected(3),
	// 否認/差戻し
	Deny(4);
	public final int value;
}
