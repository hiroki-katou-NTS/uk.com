package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apprestrictionsetting.AppLimitSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.ReceptionRestrictionSetCommand;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.AppSetForProxyAppDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.ReceptionRestrictionSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;

import java.util.List;
import java.util.stream.Collectors;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
public class ApplicationSettingCommand {
	/**
	 * ??????????????????
	 */
	private AppLimitSettingCommand appLimitSetting;
	
	/**
	 * ???????????????
	 */
	private List<AppTypeSettingCommand> appTypeSettings;
	
	/**
	 * ??????????????????????????????????????????
	 */
	private List<AppSetForProxyAppCommand> appSetForProxyApps;
	
	/**
	 * ????????????
	 */
	private List<AppDeadlineSettingCommand> appDeadlineSettings;
	
	/**
	 * ??????????????????
	 */
	private AppDisplaySettingCommand appDisplaySetting;
	
	/**
	 * ??????????????????
	 */
	private List<ReceptionRestrictionSetCommand> receptionRestrictionSettings;
	
	/**
	 * ???????????????????????????
	 */
	private int recordDate;

	public ApplicationSetting toDomain(String companyID) {
		return new ApplicationSetting(
				companyID,
				appLimitSetting.toDomain(),
				appTypeSettings.stream().map(AppTypeSettingCommand::toDomain).collect(Collectors.toList()),
				appSetForProxyApps.stream().map(AppSetForProxyAppCommand::toDomain).collect(Collectors.toList()),
				appDeadlineSettings.stream().map(AppDeadlineSettingCommand::toDomain).collect(Collectors.toList()),
				appDisplaySetting.toDomain(),
				receptionRestrictionSettings.stream().map(ReceptionRestrictionSetCommand::toDomain).collect(Collectors.toList()),
				EnumAdaptor.valueOf(recordDate, RecordDate.class));
	}
}
