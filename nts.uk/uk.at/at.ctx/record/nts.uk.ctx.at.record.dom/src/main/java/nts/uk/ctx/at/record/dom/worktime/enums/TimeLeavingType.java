package nts.uk.ctx.at.record.dom.worktime.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeLeavingType {

	DAI_TEMPORARY_TIME(0),

	DAI_LEAVING_WORK(1);
	
	public final int value;
}
