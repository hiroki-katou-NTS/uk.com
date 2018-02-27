package nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface WorktypeDisRepository {

	/**
	 * Add Worktype
	 * @param worktypeDis
	 */
	void add(WorktypeDis worktypeDis);

	/**
	 * Update Worktype
	 * @param worktypeDis
	 */
	void update(WorktypeDis worktypeDis);

	/**Find all by CID
	 * 
	 * @param companyId
	 * @return
	 */
	List<WorktypeDis> findByCompanyId(String companyId);

	/**
	 * Find by CID
	 * @param companyId
	 * @return
	 */
	Optional<WorktypeDis> findByCId(String companyId);

}
