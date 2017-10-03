package nts.uk.ctx.bs.person.dom.person.setting.selection;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor 
@IntegerRange(max = 1, min = 0)
public enum SelectionCodeCharacter {
	/** kjk */
	NUMBER_TYPE(0),//半角数字
	CHARATERS_TYPE(1);//半角英数字
	
	public final int value;
}
