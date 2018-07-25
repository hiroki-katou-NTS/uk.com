package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampCardEditMethod {

	
	//前ゼロ
	PreviousZero(1),
	
	//後ゼロ
	AfterZero(2),
	
	//前スペース
	PreviousSpace(3),
	
	//後スペース
	AfterSpace(4);
	
	public final int value;
	
}
