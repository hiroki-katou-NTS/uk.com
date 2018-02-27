package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface PerWorkplaceRepository {

	/**
	 * Find by CID
	 * @param companyId
	 * @param roleId
	 * @param functionNoWorkplace
	 * @return
	 */
	Optional<PerWorkplace> findByCId(String companyId, String roleId, int functionNoWorkplace);

	/**
	 * Update Per Workplace
	 * @param author
	 */
	void update(PerWorkplace author);

	/**
	 * Add Per Workplace
	 * @param author
	 */
	void add(PerWorkplace author);

	/**
	 * Find all by CID
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	List<PerWorkplace> findByCompanyId(String companyId, String roleId);

}
