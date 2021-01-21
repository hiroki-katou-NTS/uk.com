package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//開始終了区分
public enum StartEndClassification {
	
	START(0,"開始"),
	
	END(1,"終了");
	
	public int value;
	
	public String name;
}
