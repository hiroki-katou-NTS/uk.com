package nts.uk.ctx.pereg.dom.usesetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 3, min = 1)
public enum EmpCodeValType {
	//頭文字指定 (InitialDesignation)
	INIT_DESIGNATION(1),
	//空白 (Blank)
	BLANK(2),
	//最大値 (MaxValue)
	MAXVALUE(3);
	
	public final int value;
}
