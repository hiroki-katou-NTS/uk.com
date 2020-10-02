/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.applicable;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmploymentCondition.
 */
// 雇用条件
@Getter
public class EmploymentCondition extends DomainObject {

	/** The emp cd. */
	// 雇用コード
	private String empCd;

	/** The emp applicable atr. */
	// 雇用適用区分
	private EmpApplicableAtr empApplicableAtr;

	/**
	 * Instantiates a new employment condition.
	 *
	 * @param empCd
	 *            the emp cd
	 * @param empApplicableAtr
	 *            the emp applicable atr
	 */
	public EmploymentCondition(String empCd, int empApplicableAtr) {
		this.empCd = empCd;
		this.empApplicableAtr = EnumAdaptor.valueOf(empApplicableAtr, EmpApplicableAtr.class);
	}
}
