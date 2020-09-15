package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

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
		Optional<KrqmtApplicationSet> optEntity = this.queryProxy().find(domain.getCompanyID(), KrqmtApplicationSet.class);
		if (optEntity.isPresent()) {
			KrqmtApplicationSet oldEntity = optEntity.get();
			KrqmtApplicationSet newEntity = KrqmtApplicationSet.create(domain, reasonDisplaySettings, nightOvertimeReflectAtr);
			newEntity.setContractCd(oldEntity.getContractCd());
			newEntity.getAppDeadlineSetings().forEach(ads -> {
			    ads.setContractCd(oldEntity.getContractCd());
            });
			newEntity.getAppProxySettings().forEach(aps -> {
			    aps.setContractCd(oldEntity.getContractCd());
            });
			newEntity.getAppTypeSettings().forEach(ats -> {
			    ats.setContractCd(oldEntity.getContractCd());
            });
			this.commandProxy().update(newEntity);
		} else {
			this.commandProxy().insert(KrqmtApplicationSet.create(domain, reasonDisplaySettings, nightOvertimeReflectAtr));
		}
	}

}
