package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySettingPK;
@Stateless
public class JpaWorkplaceBPSetting extends JpaRepository implements WPBonusPaySettingRepository {

	@Override
	public List<WorkplaceBonusPaySetting> getListSetting(List<String> lstWorkplace) {
		ArrayList<WorkplaceBonusPaySetting> lstWorkplaceBonusPaySetting = new ArrayList<WorkplaceBonusPaySetting>();
		lstWorkplace.forEach(a -> lstWorkplaceBonusPaySetting
				.add(this.queryProxy().find(new KbpstWPBonusPaySettingPK(a), KbpstWPBonusPaySetting.class)
						.map(c -> this.toWorkPlaceSettingDomain(c)).get()));
		return lstWorkplaceBonusPaySetting;
	}

	@Override
	public void addListSetting(List<WorkplaceBonusPaySetting> lstSetting) {
		List<KbpstWPBonusPaySetting> lstKbpstWPBonusPaySetting = lstSetting.stream()
				.map(c -> toWorkPlaceSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpstWPBonusPaySetting);
	}

	@Override
	public void updateListSetting(List<WorkplaceBonusPaySetting> lstSetting) {
		List<KbpstWPBonusPaySetting> lstKbpstWPBonusPaySetting = lstSetting.stream()
				.map(c -> toWorkPlaceSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().updateAll(lstKbpstWPBonusPaySetting);
	}

	@Override
	public void removeListSetting(List<WorkplaceBonusPaySetting> lstSetting) {
		List<KbpstWPBonusPaySetting> lstKbpstWPBonusPaySetting = lstSetting.stream()
				.map(c -> toWorkPlaceSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().removeAll(lstKbpstWPBonusPaySetting);
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

}
