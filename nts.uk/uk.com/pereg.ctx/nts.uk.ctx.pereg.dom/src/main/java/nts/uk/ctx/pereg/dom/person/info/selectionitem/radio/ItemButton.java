package nts.uk.ctx.pereg.dom.person.info.selectionitem.radio;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum ItemButton {
	//しない
	NOTDO(0),
	//する
	DO(1);
	
	public final int value;

}
