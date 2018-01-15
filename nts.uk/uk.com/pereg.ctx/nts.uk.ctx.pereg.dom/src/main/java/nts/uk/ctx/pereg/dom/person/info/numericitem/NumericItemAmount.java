package nts.uk.ctx.pereg.dom.person.info.numericitem;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NumericItemAmount {
	//0:しない(no)
	NO(0),
	
	//1:する(yes)
	YES(1);
	
	public final int value;
}
