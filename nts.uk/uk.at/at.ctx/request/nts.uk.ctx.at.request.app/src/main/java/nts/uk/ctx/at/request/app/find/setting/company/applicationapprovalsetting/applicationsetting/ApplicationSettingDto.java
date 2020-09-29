package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.ReceptionRestrictionSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
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
	private List<AppTypeSettingDto> appTypeSetting;
	
	/**
	 * 代行申請で利用できる申請設定
	 */
	private List<AppSetForProxyAppDto> appSetForProxyApp;
	
	/**
	 * 締切設定
	 */
	private List<AppDeadlineSettingDto> appDeadlineSetLst;
	
	/**
	 * 申請表示設定
	 */
	private AppDisplaySettingDto appDisplaySetting;
	
	/**
	 * 受付制限設定
	 */
	private List<ReceptionRestrictionSetDto> receptionRestrictionSetting;
	
	/**
	 * 承認ルートの基準日
	 */
	private int recordDate;
	
	public static ApplicationSettingDto fromDomain(ApplicationSetting applicationSetting) {
		return new ApplicationSettingDto(
				applicationSetting.getCompanyID(), 
				applicationSetting.getAppLimitSetting(),
				applicationSetting.getAppTypeSettings().stream().map(a -> AppTypeSettingDto.fromDomain(a)).collect(Collectors.toList()),
				applicationSetting.getAppSetForProxyApps().stream().map(a -> AppSetForProxyAppDto.fromDomain(a)).collect(Collectors.toList()),
				applicationSetting.getAppDeadlineSetLst().stream().map(x -> AppDeadlineSettingDto.fromDomain(x)).collect(Collectors.toList()), 
				AppDisplaySettingDto.fromDomain(applicationSetting.getAppDisplaySetting()),
				applicationSetting.getReceptionRestrictionSettings().stream().map(a -> ReceptionRestrictionSetDto.fromDomain(a)).collect(Collectors.toList()),
				applicationSetting.getRecordDate().value);
	}

	public ApplicationSetting toDomain() {
		return new ApplicationSetting(
				companyID,
				appLimitSetting,
				appTypeSetting.stream().map(AppTypeSettingDto::toDomain).collect(Collectors.toList()),
				appSetForProxyApp.stream().map(AppSetForProxyAppDto::toDomain).collect(Collectors.toList()),
				appDeadlineSetLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()),
				appDisplaySetting.toDomain(),
				receptionRestrictionSetting.stream().map(ReceptionRestrictionSetDto::toDomain).collect(Collectors.toList()),
				EnumAdaptor.valueOf(recordDate, RecordDate.class));
	}
}
