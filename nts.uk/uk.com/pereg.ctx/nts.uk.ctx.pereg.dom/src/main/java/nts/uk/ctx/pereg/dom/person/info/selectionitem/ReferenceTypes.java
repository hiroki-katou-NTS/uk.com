package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 3, min = 1)
public enum ReferenceTypes {
	// 1:専用マスタ(DesignatedMaster)
	DESIGNATED_MASTER(1),

	// 2:コード名称(CodeName)
	CODE_NAME(2),

	// 3:列挙型(Enum)
	ENUM(3);

	public final int value;
}
