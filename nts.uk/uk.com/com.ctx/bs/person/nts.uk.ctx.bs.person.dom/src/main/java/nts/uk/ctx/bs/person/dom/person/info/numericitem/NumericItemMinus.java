package nts.uk.ctx.bs.person.dom.person.info.numericitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NumericItemMinus {
	//0:しない(no)
	NO(0),
	
	//1:する(yes)
	YES(1);
	
	private final int value;
}
