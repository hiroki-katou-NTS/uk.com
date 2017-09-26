/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.applicable;

import javax.ejb.Stateless;

/**
 * The Class EmpConditionFinder.
 */
@Stateless
public class EmpConditionFinder {

	/**
	 * Find.
	 *
	 * @return the emp condition dto
	 */
	public EmpConditionDto find() {
		return new EmpConditionDto();
	}
}
