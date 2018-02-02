package nts.uk.ctx.at.request.app.find.setting.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

@AllArgsConstructor
@Data
public class ApprovalFunctionSettingDto {
	public int appType;
	public String memo;
	public int useAtr;
	public int prerequisiteForpauseFlg;
	public int otAppSettingFlg;
	public int holidayTimeAppCalFlg;
	public int lateOrLeaveAppCancelFlg;
	public int lateOrLeaveAppSettingFlg;
	public int breakInputFieldDisFlg;
	public int breakTimeDisFlg;
	public int atworkTimeBeginDisFlg;
	public int goOutTimeBeginDisFlg;
	public int timeCalUseAtr;
	public int timeInputUseAtr;
	public int requiredInstructionFlg;
	public static ApprovalFunctionSettingDto convertToDto(ApprovalFunctionSetting domain) {
		if(domain==null) return null;
		return new ApprovalFunctionSettingDto(
				domain.getAppUseSetting().getAppType().value, 
				domain.getAppUseSetting().getMemo().v(), 
				domain.getAppUseSetting().getUserAtr().value, 
				domain.getPrerequisiteForpause().value, 
				domain.getOvertimeAppSetting().value, 
				domain.getHolidayTimeAppCal().value, 
				domain.getLateOrLeaveAppCancelFlg().value, 
				domain.getLateOrLeaveAppSettingFlg().value, 
				domain.getApplicationDetailSetting().get().getBreakInputFieldDisp() ? 1 : 0, 
				domain.getApplicationDetailSetting().get().getBreakTimeDisp() ? 1 : 0, 
				domain.getApplicationDetailSetting().get().getAtworkTimeBeginDisp().value, 
				domain.getApplicationDetailSetting().get().getGoOutTimeBeginDisp() ? 1 : 0, 
				domain.getApplicationDetailSetting().get().getTimeCalUse().value, 
				domain.getApplicationDetailSetting().get().getTimeInputUse().value, 
				domain.getApplicationDetailSetting().get().getRequiredInstruction() ? 1 : 0);
	}
}
