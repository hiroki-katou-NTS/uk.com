package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkingTimesheetBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWTBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWTBonusPaySettingPK;

@Stateless
public class JpaWTBPSettingRepository extends JpaRepository implements WTBonusPaySettingRepository {
	private static final String SELECT_BY_COMPANYID = "SELECT c FROM KbpstWTBonusPaySetting c WHERE c.kbpstWTBonusPaySettingPK.companyId = :companyId";

	@Override
	public List<WorkingTimesheetBonusPaySetting> getListSetting(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANYID, KbpstWTBonusPaySetting.class)
				.setParameter("companyId", companyId).getList(x -> this.toDomain(x));
	}

	@Override
	public void addWTBPSetting(WorkingTimesheetBonusPaySetting domain) {
		updateWTBPSetting(domain);
	}

	@Override
	public void updateWTBPSetting(WorkingTimesheetBonusPaySetting domain) {
		Optional<WorkingTimesheetBonusPaySetting> update = getWTBPSetting(domain.getCompanyId(),
				domain.getWorkingTimesheetCode());

		if (update.isPresent()) {
			commandProxy().update(toEntity(domain));
		} else {
			commandProxy().insert(toEntity(domain));
		}
	}

	@Override
	public void removeWTBPSetting(WorkingTimesheetBonusPaySetting domain) {
		Optional<WorkingTimesheetBonusPaySetting> update = getWTBPSetting(domain.getCompanyId(),
				domain.getWorkingTimesheetCode());

		if (update.isPresent()) {
			commandProxy().remove(KbpstWTBonusPaySetting.class,
					new KbpstWTBonusPaySettingPK(domain.getCompanyId(), domain.getWorkingTimesheetCode().v()));
		}
	}

	@Override
	public Optional<WorkingTimesheetBonusPaySetting> getWTBPSetting(String companyId,
			WorkingTimesheetCode workingTimesheetCode) {
		Optional<KbpstWTBonusPaySetting> entity = this.queryProxy()
				.find(new KbpstWTBonusPaySettingPK(companyId, workingTimesheetCode.v()), KbpstWTBonusPaySetting.class);

		if (entity.isPresent()) {
			return Optional.ofNullable(toDomain(entity.get()));
		}

		return Optional.empty();
	}

	private KbpstWTBonusPaySetting toEntity(WorkingTimesheetBonusPaySetting domain) {
		return new KbpstWTBonusPaySetting(new KbpstWTBonusPaySettingPK(domain.getCompanyId().toString(),
				domain.getWorkingTimesheetCode().toString()), domain.getBonusPaySettingCode().toString());

	}

	private WorkingTimesheetBonusPaySetting toDomain(KbpstWTBonusPaySetting entity) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(entity.kbpstWTBonusPaySettingPK.companyId,
				entity.kbpstWTBonusPaySettingPK.workingTimesheetCode, entity.bonusPaySettingCode);
	}
}
