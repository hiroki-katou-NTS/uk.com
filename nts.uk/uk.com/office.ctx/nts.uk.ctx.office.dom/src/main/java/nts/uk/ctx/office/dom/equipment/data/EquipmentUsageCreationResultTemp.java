package nts.uk.ctx.office.dom.equipment.data;

import java.util.Map;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.設備利用実績作成Temp
 */
@Data
public class EquipmentUsageCreationResultTemp {

	/**
	 * エラーList
	 */
	private Map<EquipmentItemNo, BusinessException> errorMap;
	
	/**
	 * 設備利用実績データ
	 */
	// TODO
}
