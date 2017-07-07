package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWTBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWTBonusPaySettingPK;

@Stateless
public class JpaWTBPSettingRepository extends JpaRepository implements WTBonusPaySettingRepository {
	private final String SELECT_BY_COMPANYID = "SELECT c FROM KbpstWTBonusPaySetting c WHERE c.kbpstWTBonusPaySettingPK.companyId = :companyId";

	@Override
	public List<WorkingTimesheetBonusPaySetting> getListSetting(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANYID, KbpstWTBonusPaySetting.class)
				.setParameter("companyId", companyId).getList(x -> this.toWTBPSettingDomain(x));
	}

	@Override
	public void addWTBPSetting(WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting) {
		this.commandProxy().insert(this.toWTBPSettingEntity(workingTimesheetBonusPaySetting));
	}

	@Override
	public void updateWTBPSetting(WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting) {
		this.commandProxy().update(this.toWTBPSettingEntity(workingTimesheetBonusPaySetting));
	}

	@Override
	public void removeWTBPSetting(WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting) {
		Optional<WorkingTimesheetBonusPaySetting> wTBonusPaySetting = getWTBPSetting(workingTimesheetBonusPaySetting.getCompanyId(), workingTimesheetBonusPaySetting.getWorkingTimesheetCode());
		if(wTBonusPaySetting.isPresent()){
			this.commandProxy().remove(KbpstWTBonusPaySetting.class, new KbpstWTBonusPaySettingPK(workingTimesheetBonusPaySetting.getCompanyId(), workingTimesheetBonusPaySetting.getWorkingTimesheetCode().v()));
		}
	}

	private KbpstWTBonusPaySetting toWTBPSettingEntity(
			WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting) {
		return new KbpstWTBonusPaySetting(
				new KbpstWTBonusPaySettingPK(workingTimesheetBonusPaySetting.getCompanyId().toString(),
						workingTimesheetBonusPaySetting.getWorkingTimesheetCode().toString()),
				workingTimesheetBonusPaySetting.getBonusPaySettingCode().toString());

	}

	private WorkingTimesheetBonusPaySetting toWTBPSettingDomain(KbpstWTBonusPaySetting kbpstWTBonusPaySetting) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(
				kbpstWTBonusPaySetting.kbpstWTBonusPaySettingPK.companyId,
				kbpstWTBonusPaySetting.kbpstWTBonusPaySettingPK.workingTimesheetCode,
				kbpstWTBonusPaySetting.bonusPaySettingCode);
	}

	@Override
	public Optional<WorkingTimesheetBonusPaySetting> getWTBPSetting(String companyId,
			WorkingTimesheetCode workingTimesheetCode) {
		Optional<KbpstWTBonusPaySetting> kbpstWTBonusPaySetting = this.queryProxy()
				.find(new KbpstWTBonusPaySettingPK(companyId, workingTimesheetCode.v()), KbpstWTBonusPaySetting.class);
		if (kbpstWTBonusPaySetting.isPresent()) {
			return Optional.ofNullable(this.toWTBPSettingDomain(kbpstWTBonusPaySetting.get()));
		}
		return Optional.empty();
	}

}
