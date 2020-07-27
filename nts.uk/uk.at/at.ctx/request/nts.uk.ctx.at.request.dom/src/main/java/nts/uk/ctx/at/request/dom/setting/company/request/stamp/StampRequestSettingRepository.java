package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface StampRequestSettingRepository {
	
	public Optional<StampRequestSetting_Old> findByCompanyID(String companyID);
	/**
	 * update stamp request setting
	 * @param stamp
	 * @author yennth
	 */
	void updateStamp(StampRequestSetting_Old stamp);
	/**
	 * insert stamp request setting
	 * @param stamp
	 * @author yennth
	 */
	void insertStamp(StampRequestSetting_Old stamp);
	/**
	 * delete stamp request setting
	 * @param stamp
	 * @author tanlv
	 */
	void deleteStamp(String companyID);
}
