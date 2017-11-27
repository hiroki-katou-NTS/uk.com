package nts.uk.ctx.at.auth.pub.wplmanagementauthority;

import java.util.List;
import java.util.Optional;


public interface WorkPlaceAuthorityPub {
	/**
	 * get list WorkPlaceAuthority by companyId
	 * @param companyId
	 * @return
	 */
	List<WorkPlaceAuthorityExport> getAllWorkPlaceAuthority(String companyId);
	/**
	 * get list WorkPlaceAuthority by roleId
	 * @param companyId
	 * @param functionNo
	 * @return
	 */
	List<WorkPlaceAuthorityExport> getAllWorkPlaceAuthorityByRoleId(String companyId,String roleId);
	/**
	 * get WorkPlaceAuthority by id
	 * @param companyId
	 * @param roleId
	 * @param functionNo
	 * @return
	 */
	Optional<WorkPlaceAuthorityExport> getWorkPlaceAuthorityById(String companyId, String roleId,int functionNo);
	/**
	 * add new WorkPlaceAuthority
	 * @param WorkPlaceAuthority
	 */
	void addWorkPlaceAuthority(WorkPlaceAuthorityExport workPlaceAuthotity);
	/**
	 * update WorkPlaceAuthority
	 * @param WorkPlaceAuthority
	 */
	void updateWorkPlaceAuthority(WorkPlaceAuthorityExport workPlaceAuthotity);
	/**
	 * delete WorkPlaceAuthority
	 * @param companyId
	 * @param roleId
	 * @param functionNo
	 */
	void deleteWorkPlaceAuthority(String companyId, String roleId,int functionNo);
}
