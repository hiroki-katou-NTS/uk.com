package nts.uk.ctx.office.dom.equipment.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.ItemClassification;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.実績データ
 */
@Data
@AllArgsConstructor
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

	/**
	 * [C-1] 新規追加																							
	 * @param require
	 * @param cid				会社ID
	 * @param equipmentItemNo	項目NO
	 * @param actualValue		項目値
	 * @return 項目実績作成Temp									
	 */
	public static ItemCreationResultTemp createData(Require require, String cid, EquipmentItemNo equipmentItemNo,
			ActualItemUsageValue actualValue) {
		// TODO
		return null;
	}

	public static interface Require {

		/**
		 * [R-1] 設備利用実績の項目設定を取得する
		 */
		// TODO
	}
}
