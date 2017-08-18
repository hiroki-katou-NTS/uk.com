package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WorkAtr {
	/* 1日*/
	ONE_DAY(0),
	/* 午前と午後*/	
	MORNING_AND_AFTERNOON(1);
	
	public final int value;
	
}
