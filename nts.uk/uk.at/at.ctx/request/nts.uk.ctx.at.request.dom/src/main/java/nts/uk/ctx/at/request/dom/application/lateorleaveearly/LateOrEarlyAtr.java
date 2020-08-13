package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//遅刻早退区分
public enum LateOrEarlyAtr {
//	遅刻
	LATE(0,"遅刻"),
//	早退
	EARLY(1,"早退");
	
	public final int value;
	
	public final String name;
	
}
