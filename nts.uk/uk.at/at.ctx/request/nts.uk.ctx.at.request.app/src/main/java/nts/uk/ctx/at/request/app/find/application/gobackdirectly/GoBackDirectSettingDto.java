package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

@AllArgsConstructor
@Value
public class GoBackDirectSettingDto {

	GoBackDirectlyCommonSettingDto goBackSettingDto;
	/**
	 * 社員.社員名
	 */
	String employeeName;
	/**
	 * 申請定型理由
	 */
	List<ApplicationReasonDto> listReasonDto;

}
