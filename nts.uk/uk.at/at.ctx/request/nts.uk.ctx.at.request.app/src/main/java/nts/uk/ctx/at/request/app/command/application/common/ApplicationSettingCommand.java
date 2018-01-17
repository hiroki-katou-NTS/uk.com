package nts.uk.ctx.at.request.app.command.application.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class ApplicationSettingCommand {
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
	private ApplicationSetting toDomain(){
		ApplicationSetting appli = ApplicationSetting.createFromJavaType(companyID, this.getAppActLockFlg(),
				this.getAppEndWorkFlg(), this.getAppActConfirmFlg(), 
				this.getAppOvertimeNightFlg(), this.getAppActMonthConfirmFlg(), 
				this.getRequireAppReasonFlg(), this.getDisplayPrePostFlg(), 
				this.getDisplaySearchTimeFlg(), this.getManualSendMailAtr(), 
				this.getBaseDateFlg(), this.getAdvanceExcessMessDispAtr(), 
				this.getHwAdvanceDispAtr(), this.getHwActualDispAtr(), 
				this.getActualExcessMessDispAtr(), this.getOtAdvanceDispAtr(), 
				this.getOtActualDispAtr(), this.getWarningDateDispAtr(), 
				this.getAppReasonDispAtr(), this.getAppContentChangeFlg(), 
				this.getScheReflectFlg(), this.getPriorityTimeReflectFlg(), 
				this.getAttendentTimeReflectFlg());
		return appli;
	}
}
