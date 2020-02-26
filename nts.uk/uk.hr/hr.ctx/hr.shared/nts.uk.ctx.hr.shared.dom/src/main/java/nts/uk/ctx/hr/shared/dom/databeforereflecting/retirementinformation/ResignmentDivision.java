package nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation;

import lombok.AllArgsConstructor;

/**
 * 継続雇用フラグ
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
public enum ResignmentDivision {
	// 退職
	Resignment(0, "退職"),
	// 継続
	Transfer(1, "継続");

	public final int value;
	public final String name;
}
