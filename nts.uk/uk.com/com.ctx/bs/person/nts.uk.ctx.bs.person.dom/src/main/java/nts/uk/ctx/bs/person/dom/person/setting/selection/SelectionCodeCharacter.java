package nts.uk.ctx.bs.person.dom.person.setting.selection;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor // 
public enum SelectionCodeCharacter {
	/** kjk */
	Number(0),
	AlphamericCharacters(1);
	
	public final int value;
}
