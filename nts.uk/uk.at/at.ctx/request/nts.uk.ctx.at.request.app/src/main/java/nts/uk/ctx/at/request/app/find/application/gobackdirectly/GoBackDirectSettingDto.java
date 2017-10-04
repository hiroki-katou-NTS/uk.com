package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectBasicData;

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

	AppCommonSettingDto appCommonSettingDto;

	/**
	 * Convert Data Setting to DTO
	 * 
	 * @param domain
	 * @return
	 */
	public static GoBackDirectSettingDto convertToDto(GoBackDirectBasicData domain) {
		return new GoBackDirectSettingDto(
				GoBackDirectlyCommonSettingDto.convertToDto(
						domain.getGoBackDirectSet().get()),
						domain.getEmployeeName(), 
						domain.getListAppReason().stream()
							.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList()),
						AppCommonSettingDto.convertToDto(domain.getAppCommonSettingOutput()));
	}

}
