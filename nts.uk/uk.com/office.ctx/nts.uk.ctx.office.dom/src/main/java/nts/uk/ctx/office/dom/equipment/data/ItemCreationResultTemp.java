package nts.uk.ctx.office.dom.equipment.data;

import java.util.Optional;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.項目実績作成Temp
 */
@Data
public class ItemCreationResultTemp {
	
	/**
	 * 項目NO
	 */
	private EquipmentItemNo itemNo;
	
	/**
	 * エラー
	 */
	private Optional<BusinessException> errorMsg;
	
	/**
	 * 実績データ
	 */
	private Optional<ItemData> itemData;
}
