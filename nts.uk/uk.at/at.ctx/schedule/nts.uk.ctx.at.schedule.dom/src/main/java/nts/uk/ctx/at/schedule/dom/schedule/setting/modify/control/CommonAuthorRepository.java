package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface CommonAuthorRepository {

	/**Find all by CID
	 * 
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	List<CommonAuthor> findByCompanyId(String companyId, String roleId);

	/**
	 * Find by CID
	 * @param companyId
	 * @param roleId
	 * @param functionNoCommon
	 * @return
	 */
	Optional<CommonAuthor> findByCId(String companyId, String roleId, int functionNoCommon);

	/**
	 * Update Common Author
	 * @param author
	 */
	void update(CommonAuthor author);

	/**
	 * Add Common Author
	 * @param author
	 */
	void add(CommonAuthor author);

}
