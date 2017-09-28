package nts.uk.ctx.bs.person.dom.person.setting.selection;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor // 
@IntegerRange(max = 1, min = 0)
public enum SelectionCodeCharacter {
	/** kjk */
	Number(0),
	AlphamericCharacters(1);
	
	public final int value;
}
