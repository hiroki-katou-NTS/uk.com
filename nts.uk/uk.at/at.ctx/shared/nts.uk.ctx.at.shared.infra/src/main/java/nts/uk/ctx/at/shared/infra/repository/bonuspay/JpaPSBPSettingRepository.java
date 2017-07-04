package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.EmployeeId;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstPersonalBPSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstPersonalBPSettingPK;

@Stateless
public class JpaPSBPSettingRepository extends JpaRepository implements PSBonusPaySettingRepository {

	@Override
	public List<PersonalBonusPaySetting> getListSetting(List<EmployeeId> lstEmployeeId) {
		List<PersonalBonusPaySetting> lstPersonalBonusPaySetting = new ArrayList<PersonalBonusPaySetting>();
		lstEmployeeId.forEach(a -> lstPersonalBonusPaySetting
				.add(this.queryProxy().find(new KbpstPersonalBPSettingPK(a.v()), KbpstPersonalBPSetting.class)
						.map(c -> this.toPersonalBPSettingDomain(c)).get()));
		return lstPersonalBonusPaySetting;
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

	@Override
	public void addPBPSetting(PersonalBonusPaySetting personalBonusPaySetting) {
		this.commandProxy().insert(this.toPersonalBPSettingEntity(personalBonusPaySetting));
	}

	@Override
	public void updatePBPSetting(PersonalBonusPaySetting personalBonusPaySetting) {
		this.commandProxy().update(this.toPersonalBPSettingEntity(personalBonusPaySetting));
	}

	@Override
	public void removePBPSetting(PersonalBonusPaySetting personalBonusPaySetting) {
		this.commandProxy().remove(this.toPersonalBPSettingEntity(personalBonusPaySetting));
	}

	@Override
	public Optional<PersonalBonusPaySetting> getPersonalBonusPaySetting(EmployeeId employeeId) {
		return Optional.ofNullable(this.toPersonalBPSettingDomain(
				this.queryProxy().find(new KbpstPersonalBPSettingPK(employeeId.v()), KbpstPersonalBPSetting.class).get()));
	}

}
