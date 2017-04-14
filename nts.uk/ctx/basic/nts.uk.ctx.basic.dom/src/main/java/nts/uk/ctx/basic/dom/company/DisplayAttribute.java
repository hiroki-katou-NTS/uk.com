package nts.uk.ctx.basic.dom.company;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
@AllArgsConstructor
@StringCharType(CharType.NUMERIC)
public enum DisplayAttribute {
	//0:区別しない
	DO_NOT_DISTINGUISH(0),
	//1:区別する"
	DO_DISTINGUISH(1);

	public final int value;
}
