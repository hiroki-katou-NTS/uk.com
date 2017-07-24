package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;
import java.util.Optional;

public interface WorkTypeRepository {

	/**
	 * get possible work type
	 * 
	 * @param companyId
	 * @param lstPossible
	 * @return
	 */
	List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible);

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	List<WorkType> findByCompanyId(String companyId);

	/**
	 * Find by companyId and displayAtr
	 * 
	 * @param companyId,displayAtr
	 * @return List WorkType
	 */
	List<WorkType> findByCIdAndDisplayAtr(String companyId, int displayAtr);

	/**
	 * Find by companyId and workTypeCd
	 * 
	 * @param companyId
	 * @param workTypeCd
	 * @return WorkType
	 */
	Optional<WorkType> findByPK(String companyId, String workTypeCd);
}
