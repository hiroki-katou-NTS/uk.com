package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service;

import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;

/**
 * 申請承認設定の取得
 *
 */
public interface ApplicationApprovalSettingGet {
	public ApplicationSetting getData(String companyID);
}
