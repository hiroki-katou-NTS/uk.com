package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface StampRequestSettingRepository {
	
	public Optional<StampRequestSetting> findByCompanyID(String companyID);
	
}
