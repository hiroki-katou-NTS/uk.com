/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.applicable;

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

//	EmpCondition find(String companyId, String optionalItemNo);
	
//	List<EmpCondition> findAll(String companyId, List<String> optionalItemNoList);

}
