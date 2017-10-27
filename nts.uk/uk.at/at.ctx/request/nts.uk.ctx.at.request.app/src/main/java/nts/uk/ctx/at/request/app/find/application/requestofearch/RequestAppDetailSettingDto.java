package nts.uk.ctx.at.request.app.find.application.requestofearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

@Data
@AllArgsConstructor
public class RequestAppDetailSettingDto {
	public String companyId;
	public int appType;
	public String memo;
	public int userAtr;
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

	public static RequestAppDetailSettingDto convertToDto(RequestAppDetailSetting domain) {
		if(domain==null) return null;
		return new RequestAppDetailSettingDto(domain.getCompanyId(), domain.getAppType().value, domain.getMemo().v(),
				domain.getUserAtr().value, domain.getPrerequisiteForpauseFlg().value, domain.getOtAppSettingFlg().value,
				domain.getHolidayTimeAppCalFlg().value, domain.getLateOrLeaveAppCancelFlg().value,
				domain.getLateOrLeaveAppSettingFlg().value, domain.getBreakInputFieldDisFlg().value,
				domain.getBreakTimeDisFlg().value, domain.getAtworkTimeBeginDisFlg().value,
				domain.getGoOutTimeBeginDisFlg().value, domain.getTimeCalUseAtr().value,
				domain.getTimeInputUseAtr().value, domain.getRequiredInstructionFlg().value);
	}
}
