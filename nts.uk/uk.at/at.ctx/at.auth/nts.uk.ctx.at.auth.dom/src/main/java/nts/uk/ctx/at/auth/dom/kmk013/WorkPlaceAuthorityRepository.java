package nts.uk.ctx.at.auth.dom.kmk013;

import java.util.List;
import java.util.Optional;

public interface WorkPlaceAuthorityRepository {
	/**
	 * get list WorkPlaceAuthority by companyId
	 * @param companyId
	 * @return
	 */
	List<WorkPlaceAuthority> getAllWorkPlaceAuthority(String companyId);
	/**
	 * get list WorkPlaceAuthority by roleId
	 * @param companyId
	 * @param functionNo
	 * @return
	 */
	List<WorkPlaceAuthority> getAllWorkPlaceAuthorityByRoleId(String companyId,String roleId);
	/**
	 * get WorkPlaceAuthority by id
	 * @param companyId
	 * @param roleId
	 * @param functionNo
	 * @return
	 */
	Optional<WorkPlaceAuthority> getWorkPlaceAuthorityById(String companyId, String roleId,int functionNo);
	/**
	 * add new WorkPlaceAuthority
	 * @param WorkPlaceAuthorityDto
	 */
	void addWorkPlaceAuthority(WorkPlaceAuthority workPlaceAuthotity);
	/**
	 * update WorkPlaceAuthority
	 * @param WorkPlaceAuthorityDto
	 */
	void updateWorkPlaceAuthority(WorkPlaceAuthority workPlaceAuthotity);
	/**
	 * delete WorkPlaceAuthority
	 * @param companyId
	 * @param roleId
	 * @param functionNo
	 */
	void deleteWorkPlaceAuthority(String companyId, String roleId);
}
