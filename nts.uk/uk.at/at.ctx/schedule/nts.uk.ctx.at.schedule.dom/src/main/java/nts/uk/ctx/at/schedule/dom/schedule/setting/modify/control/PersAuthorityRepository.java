package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface PersAuthorityRepository {

	/**
	 * Find All By CompanyId
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	List<PersAuthority> findByCompanyId(String companyId, String roleId);

	/**
	 * Add Pers Authority
	 * @param author
	 */
	void add(PersAuthority author);

	/**
	 * Update Pers Authority
	 * @param author
	 */
	void update(PersAuthority author);

	/**
	 * Find By CompanyId
	 * @param companyId
	 * @param roleId
	 * @param getFunctionNoPers
	 * @return
	 */
	Optional<PersAuthority> findByCId(String companyId, String roleId, int getFunctionNoPers);

}
