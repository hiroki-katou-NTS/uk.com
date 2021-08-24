package nts.uk.ctx.office.dom.equipment.ClassificationMaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.office.dom.equipment.EquipmentName;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備分類マスタ.設備分類名称
 * @author NWS-DungDV
 *
 */
@StringMaxLength(30)
public class EquipmentClassificationName extends StringPrimitiveValue<EquipmentName> {
	
	private static final long serialVersionUID = 1L;

	public EquipmentClassificationName(String rawValue) {
		super(rawValue);
	}
}
