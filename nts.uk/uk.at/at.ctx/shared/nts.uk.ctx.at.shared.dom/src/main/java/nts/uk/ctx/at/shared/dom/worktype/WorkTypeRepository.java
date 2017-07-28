package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkTypeRepository.
 */
public interface WorkTypeRepository {

	/**
	 * Gets the possible work type.
	 *
	 * @param companyId the company id
	 * @param lstPossible the lst possible
	 * @return the possible work type
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
	 * Find by worktype code.
	 *
	 * @param worktypeCode the worktype code
	 * @return the optional
	 */
	List<WorkType> findByWorktypeCodeList(String companyId, List<String> worktypeCode);

	/**
	 * Find by companyId and displayAtr.
	 *
	 * @param companyId the company id
	 * @param displayAtr the display atr
	 * @return List WorkType
	 */
	List<WorkType> findByCIdAndDisplayAtr(String companyId, int displayAtr);

	/**
	 * Find by companyId and workTypeCd.
	 *
	 * @param companyId the company id
	 * @param workTypeCd the work type cd
	 * @return WorkType
	 */
	Optional<WorkType> findByPK(String companyId, String workTypeCd);
}
