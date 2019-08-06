package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;

/**
 * 対象日が申請可能かを判定する
 *
 */
public interface ApplyPossibleCheck {
	public boolean check(ApplicationType appType, GeneralDate startDate, int overTimeAtr, AppTypeDiscreteSetting appTypeDiscreteSetting, 
			int i, List<ReceptionRestrictionSetting> receptionRestrictionSetting);
}