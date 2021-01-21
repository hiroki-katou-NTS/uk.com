/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.applicable;

import java.util.List;

/**
 * The Interface EmpConditionRepository.
 */
public interface EmpConditionRepository {

	/**
	 * Update.
	 *
	 * @param dom the dom
	 */
	void update(EmpCondition dom);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param optionalItemNo the optional item no
	 * @return the emp condition
	 */
	EmpCondition find(String companyId, Integer optionalItemNo);
	
	
	List<EmpCondition> findAll(String companyId, List<Integer> optionalItemNoList);

}
