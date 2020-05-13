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
	
	Return(0, "承認解除"),
	
	Approved(2, "承認"),

	Release(4, "差戻し");

	public final int value;

	public final String name;

}