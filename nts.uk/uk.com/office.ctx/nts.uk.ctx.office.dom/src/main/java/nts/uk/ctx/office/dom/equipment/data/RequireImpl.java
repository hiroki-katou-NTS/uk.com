package nts.uk.ctx.office.dom.equipment.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentRecordItemSettingRepository;

@AllArgsConstructor
public class RequireImpl implements ItemData.Require {
	
	private EquipmentRecordItemSettingRepository repository;

	@Override
	public Optional<EquipmentUsageRecordItemSetting> getItemSetting(String cid, String itemNo) {
		return this.repository.findByCidAndItemNo(cid, itemNo);
	}

}
