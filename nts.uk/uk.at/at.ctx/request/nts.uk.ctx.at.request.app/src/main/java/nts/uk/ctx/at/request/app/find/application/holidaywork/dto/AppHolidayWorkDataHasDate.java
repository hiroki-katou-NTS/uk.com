package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHolidayWorkPreAndReferDto;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

/**
 * 申請表示情報(基準日関係あり)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppHolidayWorkDataHasDate {
	// 申請承認機能設定
	ApprovalFunctionSetting approvalFunctionSetting;
	// 雇用別申請承認設定
	List<AppEmploymentSetting> lstEmploymentWt;
	// 就業時間帯の設定
	List<String> listWorkTimeCodes;
	// 承認ルート
	ApprovalRootPattern approvalRootPattern;
	// 事前事後区分
	int prePostAtr;
	// 基準日
	GeneralDate baseDate;
	// 表示する実績内容
	ActualStatusCheckResult actualStatusCheckResult;
	// 表示する事前申請内容
	PreAppCheckResult preAppCheckResult;
}
