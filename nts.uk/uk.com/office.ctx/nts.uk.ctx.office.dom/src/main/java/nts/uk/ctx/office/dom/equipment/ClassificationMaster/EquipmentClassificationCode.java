package nts.uk.ctx.office.dom.equipment.ClassificationMaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.office.dom.equipment.EquipmentName;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備分類マスタ.設備分類コード
 * @author NWS-DungDV
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(5)
public class EquipmentClassificationCode extends StringPrimitiveValue<EquipmentName> {

	private static final long serialVersionUID = 1L;

	public EquipmentClassificationCode(String rawValue) {
		super(rawValue);
	}
}
