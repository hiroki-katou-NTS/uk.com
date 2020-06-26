package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface ApplicationSettingRepository {
	
	public ApplicationSetting findByAppType(String companyID, ApplicationType appType);
	
}
