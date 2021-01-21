package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;

public interface AppStampSettingRepository {
	
	public Optional<AppStampSetting> findByAppID(String companyID);

	public void addStamp(AppStampSetting appStamp);

	public void updateStamp(AppStampSetting appStamp);

	public void delete(String companyID, String appID);
}
