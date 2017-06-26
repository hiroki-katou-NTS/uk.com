package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstPersonalBPSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstPersonalBPSettingPK;
@Stateless
public class JpaPSBPSettingRepository extends JpaRepository implements PSBonusPaySettingRepository {
	//private final String SELECT_BY_COMPANYID = "SELECT c FROM KbpstPersonalBPSetting c WHERE c.KbpstPersonalBPSetting.companyId = :companyId";

	@Override
	public List<PersonalBonusPaySetting> getListSetting(List<String> lstEmployeeId) {
		List<PersonalBonusPaySetting> lstPersonalBonusPaySetting = new ArrayList<PersonalBonusPaySetting>();
		lstEmployeeId.forEach(a -> lstPersonalBonusPaySetting
				.add(this.queryProxy().find(new KbpstPersonalBPSettingPK(a), KbpstPersonalBPSetting.class)
						.map(c -> this.toPersonalBPSettingDomain(c)).get()));
		return lstPersonalBonusPaySetting;
	}

	@Override
	public void addListSetting(List<PersonalBonusPaySetting> lstSetting) {
		List<KbpstPersonalBPSetting> lstKbpstPersonalBPSetting = lstSetting.stream()
				.map(c -> toPersonalBPSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpstPersonalBPSetting);
	}

	@Override
	public void updateListSetting(List<PersonalBonusPaySetting> lstSetting) {
		List<KbpstPersonalBPSetting> lstKbpstPersonalBPSetting = lstSetting.stream()
				.map(c -> toPersonalBPSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().updateAll(lstKbpstPersonalBPSetting);
	}

	@Override
	public void removeListSetting(List<PersonalBonusPaySetting> lstSetting) {
		List<KbpstPersonalBPSetting> lstKbpstPersonalBPSetting = lstSetting.stream()
				.map(c -> toPersonalBPSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().removeAll(lstKbpstPersonalBPSetting);
	}

	private KbpstPersonalBPSetting toPersonalBPSettingEntity(PersonalBonusPaySetting personalBonusPaySetting) {
		return new KbpstPersonalBPSetting(
				new KbpstPersonalBPSettingPK(personalBonusPaySetting.getEmployeeId().toString()),
				personalBonusPaySetting.getBonusPaySettingCode().toString());
	}

	private PersonalBonusPaySetting toPersonalBPSettingDomain(KbpstPersonalBPSetting kbpstPersonalBPSetting) {
		return PersonalBonusPaySetting.createFromJavaType(kbpstPersonalBPSetting.kbpstPersonalBPSettingPK.employeeId,
				kbpstPersonalBPSetting.bonusPaySettingCode);
	}

}
