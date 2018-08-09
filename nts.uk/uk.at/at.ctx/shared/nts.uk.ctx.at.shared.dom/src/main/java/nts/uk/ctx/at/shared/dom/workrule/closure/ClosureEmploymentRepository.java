/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ClosureEmploymentRepository.
 */
public interface ClosureEmploymentRepository {
	
	/**
	 * Adds the list clousure emp.
	 *
	 * @param companyID the company ID
	 * @param listClosureEmpDom the list closure emp dom
	 */
	//Add by insert and delete list ClosureEmployment.
	public void addListClousureEmp(String companyID, List<ClosureEmployment> listClosureEmpDom);
	
	/**
	 * Find by employment CD.
	 *
	 * @param companyID the company ID
	 * @param employmentCD the employment CD
	 * @return the optional
	 */
	public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD);
	
	/**
	 * Find list employment.
	 *
	 * @param companyId the company id
	 * @param employmentCDs the employment C ds
	 * @return the list
	 */
	List<ClosureEmployment> findListEmployment(String companyId, List<String> employmentCDs);
	/**
	 * get data by closureId
	 * @param companyId
	 * @param closureId
	 * @return
	 */
	List<ClosureEmployment> findByClosureId(String companyId, int closureId);	
	
	/**
	 * Find by closure id.
	 *
	 * @param companyId the company id
	 * @param closureIds the closure ids
	 * @return the list
	 */
	List<ClosureEmployment> findByClosureIds(String companyId, List<Integer> closureIds);
	
	/**
	 * Removes the clousure emp.
	 *
	 * @param companyID the company ID
	 * @param employmentCD the employment CD
	 */
	void removeClousureEmp(String companyID, String employmentCD);
}
