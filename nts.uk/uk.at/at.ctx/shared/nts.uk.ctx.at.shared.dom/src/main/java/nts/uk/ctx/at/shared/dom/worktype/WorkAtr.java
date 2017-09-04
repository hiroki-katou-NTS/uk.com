package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WorkAtr {	
	// 1日
	OneDay(0),

	// 午前
	Monring(1),
	
	// 午後
	Afternoon(2);

	/** The value. */
	public final int value;

}
