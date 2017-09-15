package nts.uk.ctx.at.request.dom.setting.stamp;

public interface StampRequestSettingRepository {
	
	public StampRequestSetting findByCompanyID(String companyID);
	
}
