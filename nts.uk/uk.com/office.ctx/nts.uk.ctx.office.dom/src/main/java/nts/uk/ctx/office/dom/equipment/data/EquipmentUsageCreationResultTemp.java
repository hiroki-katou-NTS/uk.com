package nts.uk.ctx.office.dom.equipment.data;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.error.ErrorMessage;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.設備利用実績作成Temp
 */
@Data
@AllArgsConstructor
public class EquipmentUsageCreationResultTemp {

	/**
	 * エラーList
	 */
	private Map<EquipmentItemNo, ErrorMessage> errorMap;
	
	/**
	 * 設備利用実績データ
	 */
	private Optional<EquipmentData> equipmentData;
}
