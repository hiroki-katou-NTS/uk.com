package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApplicationSettingDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請制限設定
	 */
	private AppLimitSetting appLimitSetting;
	
	/**
	 * 種類別設定
	 */
	private AppTypeSettingDto appTypeSetting;
	
	/**
	 * 代行申請で利用できる申請設定
	 */
	private AppSetForProxyAppDto appSetForProxyApp;
	
	/**
	 * 締切設定
	 */
	private AppDeadlineSettingDto appDeadlineSetting;
	
	/**
	 * 申請表示設定
	 */
	private AppDisplaySettingDto appDisplaySetting;
	
	/**
	 * 受付制限設定
	 */
	private ReceptionRestrictionSetDto receptionRestrictionSetting;
	
	/**
	 * 承認ルートの基準日
	 */
	private int recordDate;
	
	public static ApplicationSettingDto fromDomain(ApplicationSetting applicationSetting) {
		return new ApplicationSettingDto(
				applicationSetting.getCompanyID(), 
				applicationSetting.getAppLimitSetting(), 
				AppTypeSettingDto.fromDomain(applicationSetting.getAppTypeSetting()), 
				AppSetForProxyAppDto.fromDomain(applicationSetting.getAppSetForProxyApp()), 
				AppDeadlineSettingDto.fromDomain(applicationSetting.getAppDeadlineSetting()), 
				AppDisplaySettingDto.fromDomain(applicationSetting.getAppDisplaySetting()), 
				ReceptionRestrictionSetDto.fromDomain(applicationSetting.getReceptionRestrictionSetting()), 
				applicationSetting.getRecordDate().value);
	}
	
	public ApplicationSetting toDomain() {
		return new ApplicationSetting(
				companyID, 
				appLimitSetting, 
				appTypeSetting.toDomain(), 
				appSetForProxyApp.toDomain(), 
				appDeadlineSetting.toDomain(), 
				appDisplaySetting.toDomain(), 
				receptionRestrictionSetting.toDomain(), 
				EnumAdaptor.valueOf(recordDate, RecordDate.class));
	}
}
