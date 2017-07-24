package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;
import java.util.Optional;


// TODO: Auto-generated Javadoc
/**
 * The Interface WorkTypeRepository.
 */
public interface WorkTypeRepository {

	/**
	 * get possible work type.
	 *
	 * @param companyId the company id
	 * @param lstPossible the lst possible
	 * @return the possible work type
	 */
	List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible);
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<WorkType> findByCompanyId(String companyId);
	
	/**
	 * Find by worktype code.
	 *
	 * @param worktypeCode the worktype code
	 * @return the optional
	 */
	Optional<WorkType> findByWorktypeCode(String worktypeCode);
}
