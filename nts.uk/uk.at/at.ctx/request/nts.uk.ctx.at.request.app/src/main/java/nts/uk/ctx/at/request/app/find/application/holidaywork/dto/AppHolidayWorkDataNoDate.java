package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;

/**
 * 申請表示情報(基準日関係なし)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppHolidayWorkDataNoDate {
	// 社員情報
	List<EmployeeOvertimeDto> employeeOTs;
	// 申請承認設定
	AppCommonSettingOutput appCommonSettingOutput;
	// 申請定型理由リスト
	List<ApplicationReasonDto> applicationReasonDtos;
}
