package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.RequestSettingDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput_Old;

@AllArgsConstructor
@NoArgsConstructor
public class AppDispInfoNoDateDto {
	
	/**
	 * 社員情報
	 */
	public List<EmployeeInfoImport> employeeInfoLst;
	
	/**
	 * 申請承認設定
	 */
	public RequestSettingDto requestSetting;
	
	/**
	 * 申請定型理由リスト
	 */
	public List<ApplicationReasonDto> appReasonLst;
	
	public static AppDispInfoNoDateDto fromDomain(AppDispInfoNoDateOutput_Old appDispInfoNoDateOutput) {
		AppDispInfoNoDateDto appDispInfoNoDateDto = new AppDispInfoNoDateDto();
		appDispInfoNoDateDto.employeeInfoLst = appDispInfoNoDateOutput.getEmployeeInfoLst();
		appDispInfoNoDateDto.requestSetting = RequestSettingDto.fromDomain(appDispInfoNoDateOutput.getRequestSetting());
		appDispInfoNoDateDto.appReasonLst = appDispInfoNoDateOutput.getAppReasonLst().stream()
				.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList());
		return appDispInfoNoDateDto;
	}
	
	public AppDispInfoNoDateOutput_Old toDomain() {
		return new AppDispInfoNoDateOutput_Old(
				employeeInfoLst, 
				requestSetting.toDomain(), 
				appReasonLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
	
}
