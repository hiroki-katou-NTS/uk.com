package nts.uk.ctx.office.dom.equipment.data;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationCode;
import nts.uk.ctx.office.dom.equipment.information.EquipmentCode;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備利用実績データ.設備利用実績データ
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentData extends AggregateRoot {

	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate;
	
	/**
	 * 利用日
	 */
	private GeneralDate useDate;
	
	/**
	 * 利用者ID
	 */
	private String sid;
	
	/**
	 * 設備コード
	 */
	private EquipmentCode equipmentCode;
	
	/**
	 * 設備分類コード
	 */
	private EquipmentClassificationCode equipmentClassificationCode;
	
	/**
	 * 項目データ
	 */
	private List<ItemData> itemDatas;
}
