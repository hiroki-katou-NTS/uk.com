package nts.uk.ctx.at.request.dom.applicationapproval.setting.stamp;

public interface StampRequestSettingRepository {
	
	public StampRequestSetting findByCompanyID(String companyID);
	
}
