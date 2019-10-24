package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;

/**
 * 基準日として扱う日の取得
 *
 */
public interface BaseDateGet {
	public GeneralDate getBaseDate(ApplicationSetting applicationSetting, GeneralDate appDate);
}
