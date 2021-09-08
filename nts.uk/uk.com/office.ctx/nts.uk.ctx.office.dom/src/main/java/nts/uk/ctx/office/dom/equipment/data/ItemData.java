package nts.uk.ctx.office.dom.equipment.data;

import java.util.Optional;

import lombok.Data;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.実績データ
 */
@Data
public class ItemData {

	/**
	 * 項目NO
	 */
	private EquipmentItemNo itemNo;
	
	/**
	 * 項目分類
	 */
	private ItemClassification itemClassification;
	
	/**
	 * 項目値
	 */
	private Optional<ActualItemUsageValue> actualValue;
}
