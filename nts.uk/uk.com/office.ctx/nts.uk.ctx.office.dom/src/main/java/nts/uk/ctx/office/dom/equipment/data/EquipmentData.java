package nts.uk.ctx.office.dom.equipment.data;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationCode;
import nts.uk.ctx.office.dom.equipment.data.ItemData.Require;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.設備利用実績データ
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EquipmentData extends AggregateRoot {

	/**
	 * 入力日
	 */
	private final GeneralDateTime inputDate;

	/**
	 * 利用日
	 */
	private final GeneralDate useDate;

	/**
	 * 利用者ID
	 */
	private final String sid;

	/**
	 * 設備コード
	 */
	private final EquipmentCode equipmentCode;

	/**
	 * 設備分類コード
	 */
	private final EquipmentClassificationCode equipmentClassificationCode;

	/**
	 * 項目データ
	 */
	private List<ItemData> itemDatas;

	/**
	 * [C-1] 新規登録																							
	 * @param require
	 * @param cid							会社ID
	 * @param equipmentClassificationCode	設備分類コード
	 * @param equipmentCode					設備コード
	 * @param sid							利用者ID
	 * @param useDate						利用日
	 * @param itemDataMap					利用日
	 * @return 設備利用実績作成Temp									
	 */
	public static EquipmentUsageCreationResultTemp createData(Require require, String cid,
			EquipmentClassificationCode equipmentClassificationCode, EquipmentCode equipmentCode, String sid,
			GeneralDate useDate, Map<EquipmentItemNo, ActualItemUsageValue> itemDataMap) {
		// TODO
		return null;
	}
}
