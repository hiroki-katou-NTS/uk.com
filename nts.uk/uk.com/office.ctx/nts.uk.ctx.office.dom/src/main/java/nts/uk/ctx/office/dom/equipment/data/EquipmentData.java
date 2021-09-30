package nts.uk.ctx.office.dom.equipment.data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.error.ErrorMessage;
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
	 * 
	 * @param require
	 * @param cid                         会社ID
	 * @param equipmentClassificationCode 設備分類コード
	 * @param equipmentCode               設備コード
	 * @param sid                         利用者ID
	 * @param useDate                     利用日
	 * @param itemDataMap                 利用日
	 * @return 設備利用実績作成Temp
	 */
	public static EquipmentUsageCreationResultTemp createData(Require require, String cid,
			EquipmentClassificationCode equipmentClassificationCode, EquipmentCode equipmentCode, String sid,
			GeneralDate useDate, Map<EquipmentItemNo, ActualItemUsageValue> itemDataMap) {
		// $項目データ作成 ＝ 項目データMap：map 実績データ#新規追加(require、会社ID、$.項目NO、$.項目値)
		List<ItemCreationResultTemp> itemTempList = itemDataMap.entrySet().stream()
				.map(entry -> ItemData.createTempData(require, cid, entry.getKey(), entry.getValue()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		// $エラー情報 ＝ $項目データ作成 ： filter $.エラー.isPresent()
		// map key: $.項目NO
		// value: $.エラー
		Map<EquipmentItemNo, ErrorMessage> errorMsgMap = itemTempList.stream()
				.filter(data -> data.getErrorMsg().isPresent())
				.collect(Collectors.toMap(ItemCreationResultTemp::getItemNo, data -> data.getErrorMsg().get()));
		// if $エラー情報.isEmpty() ＝＝ false
		if (!errorMsgMap.isEmpty()) {
			// return 設備利用実績作成Temp#作成する($エラー情報、Optional.Empty)
			return new EquipmentUsageCreationResultTemp(errorMsgMap, Optional.empty());
		}
		// $項目データ ＝ $項目データ作成 map：$.実績データ
		List<ItemData> itemDatas = itemTempList.stream().map(ItemCreationResultTemp::getItemData)
				.map(data -> data.orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
		// $設備利用実績データ　：[mapping]
		EquipmentData equipmentData = new EquipmentData(GeneralDateTime.now(), useDate, sid,
				equipmentCode, equipmentClassificationCode, itemDatas);
		// return　設備利用実績作成Temp#作成する(Map.Empty、$設備利用実績データ)
		return new EquipmentUsageCreationResultTemp(Collections.emptyMap(), Optional.of(equipmentData));
	}

	/**
	 * [1] 変更登録
	 * 
	 * @param require
	 * @param cid         会社ID
	 * @param itemDataMap 利用日
	 * @return 設備利用実績作成Temp
	 */
	public EquipmentUsageCreationResultTemp updateItemDatas(Require require, String cid,
			Map<EquipmentItemNo, ActualItemUsageValue> itemDataMap) {
		// $項目データ作成 ＝ 項目データMap：map 実績データ#新規追加(require、会社ID、$.項目NO、$.項目値)
		List<ItemCreationResultTemp> itemTempList = itemDataMap.entrySet().stream()
				.map(entry -> ItemData.createTempData(require, cid, entry.getKey(), entry.getValue()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		// $エラー情報 ＝ $項目データ作成 ： filter $.エラー.isPresent()
		// map key: $.項目NO
		// value: $.エラー
		Map<EquipmentItemNo, ErrorMessage> errorMsgMap = itemTempList.stream()
				.filter(data -> data.getErrorMsg().isPresent())
				.collect(Collectors.toMap(ItemCreationResultTemp::getItemNo, data -> data.getErrorMsg().get()));
		// if $エラー情報.isEmpty() ＝＝ false
		if (!errorMsgMap.isEmpty()) {
			// return 設備利用実績作成Temp#作成する($エラー情報、Optional.Empty)
			return new EquipmentUsageCreationResultTemp(errorMsgMap, Optional.empty());
		}
		// $項目データ ＝ $項目データ作成 map：$.実績データ
		List<ItemData> itemDatas = itemTempList.stream().map(ItemCreationResultTemp::getItemData)
				.map(data -> data.orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
		// ＠項目データ　＝　$項目データ
		this.itemDatas = itemDatas;
		// return　設備利用実績作成Temp#作成する(Map.Empty、＠)
		return new EquipmentUsageCreationResultTemp(Collections.emptyMap(), Optional.of(this));
	}
}
