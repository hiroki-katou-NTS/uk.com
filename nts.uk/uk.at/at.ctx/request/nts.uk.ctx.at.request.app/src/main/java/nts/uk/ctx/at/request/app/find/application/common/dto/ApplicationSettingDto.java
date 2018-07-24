package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;

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
	public int classScheAchi;
	public int reflecTimeofSche;

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
				appSetting.getAttendentTimeReflectFlg().value,
				appSetting.getClassScheAchi().value,
				appSetting.getReflecTimeofSche().value);
	}
}
