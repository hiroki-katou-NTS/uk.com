package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;


public interface WorkTypeRepository {

	/**
	 * get possible work type
	 * @param companyId
	 * @param lstPossible
	 * @return
	 */
	List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible);
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<WorkType> findByCompanyId(String companyId);
}
