package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
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
	public void addListSetting(List<WorkingTimesheetBonusPaySetting> lstSetting) {
		List<KbpstWTBonusPaySetting> lstKbpstWTBonusPaySetting = lstSetting.stream().map(c -> toWTBPSettingEntity(c))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpstWTBonusPaySetting);
	}

	@Override
	public void updateListSetting(List<WorkingTimesheetBonusPaySetting> lstSetting) {
		List<KbpstWTBonusPaySetting> lstKbpstWTBonusPaySetting = lstSetting.stream().map(c -> toWTBPSettingEntity(c))
				.collect(Collectors.toList());
		this.commandProxy().updateAll(lstKbpstWTBonusPaySetting);
	}

	@Override
	public void removeListSetting(List<WorkingTimesheetBonusPaySetting> lstSetting) {
		List<KbpstWTBonusPaySetting> lstKbpstWTBonusPaySetting = lstSetting.stream().map(c -> toWTBPSettingEntity(c))
				.collect(Collectors.toList());
		this.commandProxy().removeAll(lstKbpstWTBonusPaySetting);
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

}
