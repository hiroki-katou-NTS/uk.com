package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.applimitset.AppLimitSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.AppTypeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.displaysetting.AppDisplaySettingDto;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;

@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSettingDto {
	
	/**
	 * 承認ルートの基準日
	 */
	public int recordDate;
	
	/**
	 * 申請表示設定
	 */
	public AppDisplaySettingDto appDisplaySetting;
	
	/**
	 * 受付制限設定
	 */
	public List<ReceptionRestrictionSettingDto> listReceptionRestrictionSetting;
	
	/**
	 * 申請種類別設定
	 */
	public List<AppTypeSettingDto> listAppTypeSetting;
	
	/**
	 * 申請制限設定
	 */
	public AppLimitSettingDto appLimitSetting;
	
	/**
	 * 申請締切設定
	 */
	public List<AppDeadlineSettingDto> listAppDeadlineSetting;
	
	public static ApplicationSettingDto fromDomain(ApplicationSetting applicationSetting) {
		ApplicationSettingDto applicationSettingDto = new ApplicationSettingDto();
		applicationSettingDto.recordDate = applicationSetting.getRecordDate().value;
		applicationSettingDto.appDisplaySetting = AppDisplaySettingDto.fromDomain(applicationSetting.getAppDisplaySetting());
		applicationSettingDto.listReceptionRestrictionSetting = applicationSetting.getListReceptionRestrictionSetting().stream()
				.map(x -> ReceptionRestrictionSettingDto.fromDomain(x)).collect(Collectors.toList());
		applicationSettingDto.listAppTypeSetting = applicationSetting.getListAppTypeSetting().stream()
				.map(x -> AppTypeSettingDto.fromDomain(x)).collect(Collectors.toList());
		applicationSettingDto.appLimitSetting = AppLimitSettingDto.fromDomain(applicationSetting.getAppLimitSetting());
		applicationSettingDto.listAppDeadlineSetting = applicationSetting.getListAppDeadlineSetting().stream()
				.map(x -> AppDeadlineSettingDto.fromDomain(x)).collect(Collectors.toList());
		return applicationSettingDto;
	}
}
