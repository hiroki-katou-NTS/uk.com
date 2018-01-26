package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface StampRequestSettingRepository {
	
	public Optional<StampRequestSetting> findByCompanyID(String companyID);
	/**
	 * update stamp request setting
	 * @param stamp
	 * @author yennth
	 */
	void updateStamp(StampRequestSetting stamp);
	/**
	 * insert stamp request setting
	 * @param stamp
	 * @author yennth
	 */
	void insertStamp(StampRequestSetting stamp);
	
}
