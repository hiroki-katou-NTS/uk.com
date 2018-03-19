package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 
 * @author Sonnlb
 *
 */
public class HolidayShipmentDto {

	/**
	 * 申請承認設定
	 */
	ApplicationSettingDto applicationSetting;

	/**
	 * 雇用別申請承認設定
	 */
	List<AppEmploymentSettingDto> appEmploymentSettings;
	/**
	 * 申請承認機能設定
	 */
	ApprovalFunctionSettingDto approvalFunctionSetting;
	/**
	 * 基準日
	 */
	GeneralDate refDate;

	/**
	 * 振出用勤務種類
	 */
	List<WorkTypeDto> takingOutWkTypes;
	/**
	 * 振休用勤務種類
	 */
	List<WorkTypeDto> holidayWkTypes;
	/**
	 * 申請表示設定
	 */
	int preOrPostType;

	/**
	 * 申請者社員ID
	 */
	String employeeID;

	private boolean manualSendMailAtr;

	List<ApplicationReasonDto> appReasons;

	ChangeWorkTypeDto changeWkType;

	/**
	 * 振出用就業時間帯 振休用就業時間帯 Mặc định null
	 */

}
