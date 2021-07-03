package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import lombok.AllArgsConstructor;

/**
 * 
 * @author sonnlb 介護看護区分
 */
@AllArgsConstructor
public enum CareType {
	// 介護
	Nursing(0),
	// 子の看護
	ChildNursing(1);

	public final int value;
}
