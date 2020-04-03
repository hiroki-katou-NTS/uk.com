/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

import lombok.AllArgsConstructor;

/**
 * sonnlb 承認ステータス
 */
@AllArgsConstructor
public enum ApprovalStatus {

	Unregistered(0, "未承認"),

	Approved_WaitingForReflection(2, "承認済み"),

	Deny(4, "否認/差戻し");

	public final int value;

	public final String name;

}