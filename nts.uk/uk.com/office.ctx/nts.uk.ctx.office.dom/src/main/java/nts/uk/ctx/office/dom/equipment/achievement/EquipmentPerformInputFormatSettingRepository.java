package nts.uk.ctx.office.dom.equipment.achievement;

import java.util.Optional;

public interface EquipmentPerformInputFormatSettingRepository {

	// [1] Insert(設備の実績入力フォーマット設定)																							
	void insert(EquipmentPerformInputFormatSetting domain);
	
	// [2] Delete(会社ID)																							
	void delete(String cid);
	
	// [3] Get																							
	Optional<EquipmentPerformInputFormatSetting> findByCid(String cid);
}
