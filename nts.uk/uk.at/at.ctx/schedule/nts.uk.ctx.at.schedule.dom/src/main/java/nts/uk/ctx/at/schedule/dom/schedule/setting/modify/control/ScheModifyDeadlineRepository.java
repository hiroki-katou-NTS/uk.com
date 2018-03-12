package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface ScheModifyDeadlineRepository {

	/**
	 * Add Schemodify Deadline
	 * @param deadline
	 */
	void add(SchemodifyDeadline deadline);

	/**
	 * Find all by CID
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	List<SchemodifyDeadline> findByCompanyId(String companyId, String roleId);

	/**
	 * Update Schemodify Deadline
	 * @param deadline
	 */
	void update(SchemodifyDeadline deadline);

	/**
	 * Find by CID
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	Optional<SchemodifyDeadline> findByCId(String companyId, String roleId);

}
