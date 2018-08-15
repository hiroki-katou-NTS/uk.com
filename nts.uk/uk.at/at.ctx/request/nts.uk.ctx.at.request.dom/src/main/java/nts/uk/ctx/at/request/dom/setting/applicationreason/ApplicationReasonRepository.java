package nts.uk.ctx.at.request.dom.setting.applicationreason;

import java.util.List;
import java.util.Optional;

public interface ApplicationReasonRepository {
	/**
	 * get application by company id
	 * @param companyId
	 * @return
	 */
	List<ApplicationReason> getReasonByCompanyId(String companyId);
	/**
	 * get application by company id and application type
	 * @param companyId
	 * @param appType
	 * @return
	 */
	List<ApplicationReason> getReasonByAppType(String companyId, int appType);
	/**
	 * get application by company id and application type
	 * @param companyId
	 * @param appType
	 * @return
	 */
	List<ApplicationReason> getByAppType(String companyId, int appType);
	
	/**
	 * update list application reason
	 * @param listUpdate
	 * @author yennth
	 */
	void updateReason(List<ApplicationReason> listUpdate);
	/**
	 * insert a item 
	 * @param insert
	 * @author yennth
	 */
	void insertReason(ApplicationReason insert);
	/**
	 * delete a item application reason
	 * @param companyId
	 * @param appType
	 * @param reasonID
	 * @author yentnth
	 */
	void deleteReason(String companyId, int appType, String reasonID);
	/**
	 * get application by company id and application type
	 * @param companyId
	 * @param appType
	 * @return
	 */
	List<ApplicationReason> getReasonByAppType(String companyId, int appType, String defaultResource);
	/**
	 * get getReasonById by company id and reasonID
	 * @param companyId
	 * @param appType
	 * @return
	 */
	Optional<ApplicationReason> getReasonById(String companyId, String reasonID);
}
