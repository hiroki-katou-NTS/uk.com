package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FuriClassifi {

	/** 振休*/
	SUSPENSION(0),
	
	/** 振出*/
	DRAWER(1);

	public final int value;
}
