package nts.uk.ctx.office.dom.equipment.achievement.domainservice;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentFormSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentPerformInputFormatSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentFormSettingRepository;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentFormatSettingRepository;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentRecordItemSettingRepository;

@AllArgsConstructor
public class DefaultRequireImpl implements RegisterEquipmentItemSettingMaster.Require {
	
	private EquipmentFormatSettingRepository formatSettingRepo;
	
	private EquipmentFormSettingRepository formSettingRepo;
	
	private EquipmentRecordItemSettingRepository itemSettingRepo;
	
	@Override
	public List<EquipmentUsageRecordItemSetting> getEURItemSettings(String cid) {
		return itemSettingRepo.findByCid(cid);
	}

	@Override
	public void insertAllEURItemSettings(List<EquipmentUsageRecordItemSetting> items) {
		itemSettingRepo.insertAll(items);
	}

	@Override
	public void deleteAllEURItemSettings(String cid, List<EquipmentItemNo> itemNos) {
		itemSettingRepo.deleteAll(cid, itemNos);
	}

	@Override
	public Optional<EquipmentPerformInputFormatSetting> getEURInputFormatSetting(String cid) {
		return formatSettingRepo.get(cid);
	}

	@Override
	public void insertEURInputFormatSetting(EquipmentPerformInputFormatSetting item) {
		formatSettingRepo.insert(item);
	}

	@Override
	public void deleteEURInputFormatSetting(String cid) {
		formatSettingRepo.delete(cid);
	}

	@Override
	public Optional<EquipmentFormSetting> getEquipmentFormSettings(String cid) {
		return formSettingRepo.findByCid(cid);
	}

	@Override
	public void insertEuipmentFormSettings(EquipmentFormSetting item) {
		formSettingRepo.insert(item);
	}

	@Override
	public void deleteEquipmentFormSettings(String cid) {
		formSettingRepo.delete(cid);
	}

	
}
