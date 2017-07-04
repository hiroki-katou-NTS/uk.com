package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.WorkplaceId;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySettingPK;

@Stateless
public class JpaWorkplaceBPSetting extends JpaRepository implements WPBonusPaySettingRepository {

	@Override
	public List<WorkplaceBonusPaySetting> getListSetting(List<WorkplaceId> lstWorkplace) {
		ArrayList<WorkplaceBonusPaySetting> lstWorkplaceBonusPaySetting = new ArrayList<WorkplaceBonusPaySetting>();
		lstWorkplace.forEach(a -> lstWorkplaceBonusPaySetting
				.add(this.queryProxy().find(new KbpstWPBonusPaySettingPK(a.v()), KbpstWPBonusPaySetting.class)
						.map(c -> this.toWorkPlaceSettingDomain(c)).get()));
		return lstWorkplaceBonusPaySetting;
	}

	@Override
	public void addWPBPSetting(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		this.commandProxy().insert(this.toWorkPlaceSettingEntity(workplaceBonusPaySetting));

	}

	@Override
	public void updateWPBPSetting(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		 Optional<KbpstWPBonusPaySetting> kbpstWPBonusPaySettingOptional = this.queryProxy().find(new KbpstWPBonusPaySettingPK(workplaceBonusPaySetting.getWorkplaceId().v()), KbpstWPBonusPaySetting.class);
		if(kbpstWPBonusPaySettingOptional.isPresent()){
			KbpstWPBonusPaySetting kbpstWPBonusPaySetting = kbpstWPBonusPaySettingOptional.get();
			kbpstWPBonusPaySetting.bonusPaySettingCode= workplaceBonusPaySetting.getBonusPaySettingCode().v();
			this.commandProxy().update(kbpstWPBonusPaySetting);
		}
	}

	@Override
	public void removeWPBPSetting(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		this.commandProxy().remove(this.toWorkPlaceSettingEntity(workplaceBonusPaySetting));
	}

	private KbpstWPBonusPaySetting toWorkPlaceSettingEntity(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		return new KbpstWPBonusPaySetting(
				new KbpstWPBonusPaySettingPK(workplaceBonusPaySetting.getWorkplaceId().toString()),
				workplaceBonusPaySetting.getBonusPaySettingCode().toString());
	}

	private WorkplaceBonusPaySetting toWorkPlaceSettingDomain(KbpstWPBonusPaySetting kbpstWPBonusPaySetting) {
		return WorkplaceBonusPaySetting.createFromJavaType(kbpstWPBonusPaySetting.kbpstWPBonusPaySettingPK.workplaceId,
				kbpstWPBonusPaySetting.bonusPaySettingCode);

	}

	@Override
	public Optional<WorkplaceBonusPaySetting> getWPBPSetting(WorkplaceId WorkplaceId) {
		Optional<KbpstWPBonusPaySetting> kbpstWPBonusPaySetting = this.queryProxy().find(new KbpstWPBonusPaySettingPK(WorkplaceId.v()), KbpstWPBonusPaySetting.class);
		if(kbpstWPBonusPaySetting.isPresent()){
			return Optional.ofNullable(this.toWorkPlaceSettingDomain(kbpstWPBonusPaySetting
					.get()));
		}
		return null;
	}
}
