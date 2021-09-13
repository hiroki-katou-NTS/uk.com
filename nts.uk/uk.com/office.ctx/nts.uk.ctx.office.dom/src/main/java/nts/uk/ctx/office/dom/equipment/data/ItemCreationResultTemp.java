package nts.uk.ctx.office.dom.equipment.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.error.ErrorMessage;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.項目実績作成Temp
 */
@Data
@AllArgsConstructor
public class ItemCreationResultTemp {
	
	/**
	 * 項目NO
	 */
	private EquipmentItemNo itemNo;
	
	/**
	 * エラー
	 */
	private Optional<ErrorMessage> errorMsg;
	
	/**
	 * 実績データ
	 */
	private Optional<ItemData> itemData;
}
