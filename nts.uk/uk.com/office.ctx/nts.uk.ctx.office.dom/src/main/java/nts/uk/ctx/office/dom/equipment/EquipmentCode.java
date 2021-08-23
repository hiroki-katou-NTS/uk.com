package nts.uk.ctx.office.dom.equipment;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備マスタ.設備コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(5)
public class EquipmentCode extends StringPrimitiveValue<EquipmentCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquipmentCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
	
}
