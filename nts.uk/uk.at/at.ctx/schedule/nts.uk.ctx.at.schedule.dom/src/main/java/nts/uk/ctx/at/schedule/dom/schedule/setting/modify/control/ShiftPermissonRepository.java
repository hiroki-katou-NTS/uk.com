package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface ShiftPermissonRepository {

	/**
	 * Find by CID
	 * @param companyId
	 * @param roleId
	 * @param functionNoShift
	 * @return
	 */
	Optional<ShiftPermisson> findByCId(String companyId, String roleId, int functionNoShift);

	/**
	 * Update Shift Permisson
	 * @param author
	 */
	void update(ShiftPermisson author);

	/**
	 * Add Shift Permisson
	 * @param author
	 */
	void add(ShiftPermisson author);

	/**
	 * Find all by CID
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	List<ShiftPermisson> findByCompanyId(String companyId, String roleId);

}
