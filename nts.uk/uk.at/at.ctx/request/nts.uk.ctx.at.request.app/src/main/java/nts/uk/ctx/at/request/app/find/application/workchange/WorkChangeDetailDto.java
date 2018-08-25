package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.DetailedScreenPreBootModeDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.PrelaunchAppSettingDto;
import nts.uk.ctx.at.request.dom.application.workchange.WorkChangeDetail;

@AllArgsConstructor
@Value
public class WorkChangeDetailDto {
	/**
	* 勤務変更申請
	*/
	AppWorkChangeDto workChangeDto;
	/**
	 * 申請
	 */
	ApplicationDto_New applicationDto;
	/**
	 * 申請者名
	 */
	private String employeeName;
	/**
	 * 申請者社員ID
	 */
	private String sID;
		
	DetailedScreenPreBootModeDto detailedScreenPreBootMode;

	PrelaunchAppSettingDto prelaunchAppSetting;	
	/**
	 * 画面モード(表示/編集)
	 * 0: 表示
	 * 1: 編集
	 */
	int OutMode;
	/**
	 * 選択可能な勤務種類コード
	 */
	List<String> workTypeCodes;
	/**
	 * 選択可能な就業時間帯コード
	 */
	List<String> workTimeCodes;
	
	boolean isTimeRequired;
	
	public static WorkChangeDetailDto  formDomain(WorkChangeDetail domain){
		return new WorkChangeDetailDto(AppWorkChangeDto.fromDomain(domain.getAppWorkChange()), 
				ApplicationDto_New.fromDomain(domain.getApplication()), 
				domain.getEmployeeName(), domain.getSID(), 
				DetailedScreenPreBootModeDto.convertToDto(domain.getDetailedScreenPreBootModeOutput()),
				PrelaunchAppSettingDto.convertToDto(domain.getPrelaunchAppSetting()),
				domain.getDetailScreenInitModeOutput().getOutputMode().value,
				domain.getWorkTypeCodes(), domain.getWorkTimeCodes(),
				domain.isTimeRequired());
	}
}
