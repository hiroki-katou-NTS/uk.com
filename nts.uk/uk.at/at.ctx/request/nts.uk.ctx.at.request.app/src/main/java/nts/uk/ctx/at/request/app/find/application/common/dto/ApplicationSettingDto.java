package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.applicationapproval.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.AprovalPersonFlg;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.NumDaysOfWeek;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.PriorityFLg;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.ReflectionFlg;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class ApplicationSettingDto {
	public String companyID;
	public Integer appActLockFlg;
	public Integer appEndWorkFlg;
	public Integer appActConfirmFlg;
	public Integer appOvertimeNightFlg;
	public Integer appActMonthConfirmFlg;
	public Integer requireAppReasonFlg;
	public Integer displayPrePostFlg;
	public Integer displaySearchTimeFlg;
	public Integer manualSendMailAtr;
	public Integer baseDateFlg;
	public Integer advanceExcessMessDispAtr;
	public Integer hwAdvanceDispAtr;
	public Integer hwActualDispAtr;
	public Integer actualExcessMessDispAtr;
	public Integer otAdvanceDispAtr;
	public Integer otActualDispAtr;
	public Integer warningDateDispAtr;
	public Integer appReasonDispAtr;
	public Integer appContentChangeFlg;
	public Integer scheReflectFlg;
	public Integer priorityTimeReflectFlg;
	public Integer attendentTimeReflectFlg;

	public static ApplicationSettingDto convertToDto(ApplicationSetting appSetting) {
		if(appSetting==null) return null;
		return new ApplicationSettingDto(
				appSetting.getCompanyID(), 
				appSetting.getAppActLockFlg().value,
				appSetting.getAppEndWorkFlg().value, 
				appSetting.getAppActConfirmFlg().value,
				appSetting.getAppOvertimeNightFlg().value, 
				appSetting.getAppActMonthConfirmFlg().value,
				appSetting.getRequireAppReasonFlg().value, 
				appSetting.getDisplayPrePostFlg().value,
				appSetting.getDisplaySearchTimeFlg().value, 
				appSetting.getManualSendMailAtr().value,
				appSetting.getBaseDateFlg().value, 
				appSetting.getAdvanceExcessMessDispAtr().value,
				appSetting.getHwAdvanceDispAtr().value, 
				appSetting.getHwActualDispAtr().value,
				appSetting.getActualExcessMessDispAtr().value, 
				appSetting.getOtAdvanceDispAtr().value,
				appSetting.getOtActualDispAtr().value, 
				appSetting.getWarningDateDispAtr().v(),
				appSetting.getAppReasonDispAtr().value, 
				appSetting.getAppContentChangeFlg().value,
				appSetting.getScheReflectFlg().value,
				appSetting.getPriorityTimeReflectFlg().value, 
				appSetting.getAttendentTimeReflectFlg().value);
	}
}
