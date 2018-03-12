package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface DateAuthorityRepository {

	/**
	 * Find All By CompanyId
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	List<DateAuthority> findByCompanyId(String companyId, String roleId);

	/**
	 * Add Date Authority
	 * @param author
	 */
	void add(DateAuthority author);

	/**
	 * Update Date Authority
	 * @param author
	 */
	void update(DateAuthority author);

	/**
	 * Find by CID
	 * @param companyId
	 * @param roleId
	 * @param functionNoDate
	 * @return
	 */
	Optional<DateAuthority> findByCId(String companyId, String roleId, int functionNoDate);

}
