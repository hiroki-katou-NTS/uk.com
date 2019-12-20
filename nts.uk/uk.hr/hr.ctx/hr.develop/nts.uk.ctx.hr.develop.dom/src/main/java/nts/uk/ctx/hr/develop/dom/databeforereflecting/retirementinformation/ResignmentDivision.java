package nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation;

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
	Resignment(0),
	// 継続
	Transfer(1);

	public final int value;
}
