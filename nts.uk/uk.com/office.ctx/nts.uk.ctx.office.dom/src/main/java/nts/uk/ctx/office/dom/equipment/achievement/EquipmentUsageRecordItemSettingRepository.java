package nts.uk.ctx.office.dom.equipment.achievement;

import java.util.List;
import java.util.Optional;

public interface EquipmentUsageRecordItemSettingRepository {

	// [1] InsertAll(List<設備利用実績の項目設定>)
	void insertAll(List<EquipmentUsageRecordItemSetting> domains);
	
	// [2] DeleteAll(会社ID, List<項目NO>)																							
	void deleteAll(String cid, List<EquipmentItemNo> itemNos);
	
	// [3] Get*																							
	List<EquipmentUsageRecordItemSetting> findByCid(String cid);
	
	// [4] Get																							
	Optional<EquipmentUsageRecordItemSetting> findByCidAndItemNo(String cid, String itemNo);
}
