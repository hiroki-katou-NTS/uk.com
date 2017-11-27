package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.DetailedScreenPreBootModeDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.PrelaunchAppSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.dom.application.workchange.WorkChangeDetail;

@AllArgsConstructor
@Value
public class WorkChangeDetailDto {
	/**
	* 勤務変更申請
	*/
	AppWorkChangeDto workChangeDto;
	/**
	 * 事前事後区分
	 */
	int prePostAtr;
	/**
	 * 申請理由
	 */
	String appReason;
	/**
	 * 申請日
	 */
	GeneralDate appDate;
	/**
	 * 申請者名
	 */
	private String employeeName;
	/**
	 * 申請者社員ID
	 */
	private String sID;
	
	/**
	 * 定型理由のリストにセットするため
	 */
	private List<ApplicationReasonDto> listAppReason;
	
	DetailedScreenPreBootModeDto detailedScreenPreBootMode;

	PrelaunchAppSettingDto prelaunchAppSetting;
	
	public static WorkChangeDetailDto  formDomain(WorkChangeDetail domain){
		return new WorkChangeDetailDto(AppWorkChangeDto.fromDomain(domain.getAppWorkChange()), 
				domain.getPrePostAtr(), domain.getAppReason(), domain.getAppDate(), 
				domain.getEmployeeName(), domain.getSID(), 
				domain.getListAppReason().stream().map(x -> ApplicationReasonDto.convertToDto(x))
				.collect(Collectors.toList()), 
				DetailedScreenPreBootModeDto.convertToDto(domain.getDetailedScreenPreBootModeOutput()),
				PrelaunchAppSettingDto.convertToDto(domain.getPrelaunchAppSetting()));
	}
}
