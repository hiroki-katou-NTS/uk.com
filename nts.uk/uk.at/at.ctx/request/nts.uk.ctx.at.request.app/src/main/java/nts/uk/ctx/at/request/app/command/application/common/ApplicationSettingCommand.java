package nts.uk.ctx.at.request.app.command.application.common;
/*import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;*/
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class ApplicationSettingCommand {
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
	public Integer scheduleConfirmedAtr;
	public Integer achievementConfirmedAtr;
	public int classScheAchi;
	public int reflecTimeofSche; 
	//hoatt doi ung ver53
	public int companyUnit;
	public int workplaceUnit;
	public int employeeUnit;
}
