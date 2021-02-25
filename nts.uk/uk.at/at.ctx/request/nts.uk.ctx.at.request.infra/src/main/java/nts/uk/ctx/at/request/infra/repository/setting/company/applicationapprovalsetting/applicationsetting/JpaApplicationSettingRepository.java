package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqmtRepresentApp;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.KrqmtAppMclose;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.KrqmtAppType;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.OtHdWorkAppSettingRepository;
import org.apache.commons.lang3.BooleanUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.AppSetForProxyApp;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AfterhandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.BeforehandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.OTAppBeforeAccepRestric;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqmtApplicationSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApplicationSettingRepository extends JpaRepository implements ApplicationSettingRepository, OtHdWorkAppSettingRepository {

	@Override
	public ApplicationSetting findByAppType(String companyID, ApplicationType appType) {
		Optional<ApplicationSetting> optDomain = findByCompanyId(companyID);
		if(!optDomain.isPresent()) {
			throw new RuntimeException("setting master data");
		}
		ApplicationSetting domain = optDomain.get();
		domain.getAppTypeSettings().removeIf(i -> i.getAppType() != appType);
		domain.getReceptionRestrictionSettings().removeIf(i -> i.getAppType() != appType);
		return domain;
	}

	@Override
	public Optional<ApplicationSetting> findByCompanyId(String companyId) {
		return this.queryProxy().find(companyId, KrqmtApplicationSet.class).map(KrqmtApplicationSet::toDomainApplicationSetting);
	}

	@Override
	public Integer getNightOvertimeReflectAtr(String companyId) {
		return this.queryProxy().find(companyId, KrqmtApplicationSet.class).map(KrqmtApplicationSet::getTimeNightReflectAtr).orElse(null);
	}

	@Override
	public void save(ApplicationSetting domain, List<DisplayReason> reasonDisplaySettings, int nightOvertimeReflectAtr) {
		List<KrqmtAppMclose> lstAppMclose = this.queryProxy().query("select a from KrqmtAppMclose a where a.pk.companyId = :companyId", KrqmtAppMclose.class)
				.setParameter("companyId", domain.getCompanyID()).getList();
		this.commandProxy().removeAll(lstAppMclose);

		List<KrqmtRepresentApp> lstRepresentApp = this.queryProxy().query("select a from KrqmtRepresentApp a where a.pk.companyId = :companyId", KrqmtRepresentApp.class)
				.setParameter("companyId", domain.getCompanyID()).getList();
		this.commandProxy().removeAll(lstRepresentApp);

		List<KrqmtAppType> lstAppType = this.queryProxy().query("select a from KrqmtAppType a where a.pk.companyId = :companyId", KrqmtAppType.class)
				.setParameter("companyId", domain.getCompanyID()).getList();
		this.commandProxy().removeAll(lstAppType);

		this.getEntityManager().flush();

		Optional<KrqmtApplicationSet> optEntity = this.queryProxy().find(domain.getCompanyID(), KrqmtApplicationSet.class);
		if (optEntity.isPresent()) {
			KrqmtApplicationSet entity = optEntity.get();
			entity.setBaseDateSet(domain.getRecordDate().value);
			entity.setMonAtdConfirmAtr(domain.getAppLimitSetting().isCanAppAchievementMonthConfirm() ? 1 : 0);
			entity.setAtdLockAtr(domain.getAppLimitSetting().isCanAppAchievementLock() ? 1 : 0);
			entity.setAtdConfirmAtr(BooleanUtils.toInteger(domain.getAppLimitSetting().isCanAppFinishWork()));
			entity.setDayAtdConfirmAtr(BooleanUtils.toInteger(domain.getAppLimitSetting().isCanAppAchievementConfirm()));
			entity.setReasonRequireAtr(BooleanUtils.toInteger(domain.getAppLimitSetting().isRequiredAppReason()));
			entity.setFixedReasonRequireAtr(BooleanUtils.toInteger(domain.getAppLimitSetting().isStandardReasonRequired()));
			entity.setPrePostDisplayAtr(domain.getAppDisplaySetting().getPrePostDisplayAtr().value);
			entity.setSendEmailIniAtr(domain.getAppDisplaySetting().getManualSendMailAtr().value);
			entity.setTimeNightReflectAtr(nightOvertimeReflectAtr);
			this.commandProxy().update(entity);

			this.commandProxy().insertAll(domain.getAppDeadlineSetLst().stream().map(d -> KrqmtAppMclose.create(domain.getCompanyID(), d)).collect(Collectors.toList()));
			this.commandProxy().insertAll(domain.getAppSetForProxyApps().stream().map(p -> KrqmtRepresentApp.fromDomain(p, domain.getCompanyID())).collect(Collectors.toList()));

			Map<ApplicationType, AppTypeSetting> appTypeSettingMap = domain.getAppTypeSettings().stream().collect(Collectors.toMap(AppTypeSetting::getAppType, Function.identity()));
			Map<ApplicationType, ReceptionRestrictionSetting> receptionRestrictionSettingMap = domain.getReceptionRestrictionSettings().stream().collect(Collectors.toMap(ReceptionRestrictionSetting::getAppType, Function.identity()));
			Map<ApplicationType, DisplayReason> displayReasonMap = reasonDisplaySettings.stream().filter(r -> r.getAppType() != ApplicationType.ABSENCE_APPLICATION).collect(Collectors.toMap(DisplayReason::getAppType, Function.identity()));
			List<KrqmtAppType> appTypeEntities = new ArrayList<>();
			for (ApplicationType applicationType : ApplicationType.values()) {
				AppTypeSetting appTypeSetting = appTypeSettingMap.get(applicationType);
				ReceptionRestrictionSetting receptionRestrictionSetting = receptionRestrictionSettingMap.get(applicationType);
				DisplayReason reasonDisplaySetting = displayReasonMap.get(applicationType);
				if (appTypeSetting != null && receptionRestrictionSetting != null) {
					appTypeEntities.add(KrqmtAppType.fromDomain(domain.getCompanyID(), appTypeSetting, receptionRestrictionSetting, reasonDisplaySetting));
				}
			}
			this.commandProxy().insertAll(appTypeEntities);
		} else {
			this.commandProxy().insert(KrqmtApplicationSet.create(domain, reasonDisplaySettings, nightOvertimeReflectAtr));
		}
	}

}
