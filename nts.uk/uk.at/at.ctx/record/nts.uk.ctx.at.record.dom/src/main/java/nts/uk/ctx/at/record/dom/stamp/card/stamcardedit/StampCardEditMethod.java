package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampCardEditMethod {

	
	//前ゼロ
	PreviousZero(0),
	
	//後ゼロ
	AfterZero(1),
	
	//前スペース
	PreviousSpace(2),
	
	//後スペース
	AfterSpace(3);
	
	public final int value;
	
}
