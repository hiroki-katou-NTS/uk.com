package nts.uk.ctx.office.dom.equipment.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.error.ErrorMessage;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;
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
	public static ItemCreationResultTemp createTempData(Require require, String cid, EquipmentItemNo equipmentItemNo,
			ActualItemUsageValue actualValue) {
		// $項目設定 = require.設備利用実績の項目設定を取得する(会社ID、項目NO)
		Optional<EquipmentUsageRecordItemSetting> optItemSetting = require.getItemSetting(cid, equipmentItemNo.v());
		if (optItemSetting.isPresent()) {
			EquipmentUsageRecordItemSetting itemSetting = optItemSetting.get();
			// $項目分類　＝　$項目設定.項目入力制御.項目分類
			ItemClassification itemCls = itemSetting.getInputcontrol().getItemCls();
			// $エラー　＝　$項目設定.入力した値の制御をチェックする()
			Optional<ErrorMessage> optErrorMsg = itemSetting.check(actualValue);
			// if　$エラー.isPresent()
			if (optErrorMsg.isPresent()) {
				// return　項目実績作成Temp#作成する($エラー、Optional.Empty)
				return new ItemCreationResultTemp(equipmentItemNo, optErrorMsg, Optional.empty());
			}
			// return 項目実績作成Temp#作成する(Optional.Empty、実績データ#作成する(項目NO、$項目分類、項目値))
			ItemData itemData = new ItemData(equipmentItemNo, itemCls, Optional.of(actualValue));
			return new ItemCreationResultTemp(equipmentItemNo, Optional.empty(), Optional.of(itemData));
		}
		return null;
	}

	public static interface Require {

		/**
		 * [R-1] 設備利用実績の項目設定を取得する
		 */
		Optional<EquipmentUsageRecordItemSetting> getItemSetting(String cid, String itemNo);
	}
}
