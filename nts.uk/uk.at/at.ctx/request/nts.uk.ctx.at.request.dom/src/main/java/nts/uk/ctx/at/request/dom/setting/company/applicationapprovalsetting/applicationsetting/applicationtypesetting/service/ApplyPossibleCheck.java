package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;

/**
 * 対象日が申請可能かを判定する
 *
 */
public interface ApplyPossibleCheck {
	public boolean check(ApplicationType_Old appType, GeneralDate startDate, OverTimeAtr overTimeAtr, AppTypeDiscreteSetting appTypeDiscreteSetting, 
			int i, List<ReceptionRestrictionSetting> receptionRestrictionSetting);
}