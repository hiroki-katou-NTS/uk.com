package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//打刻申請時間帯分類
public enum TimeZoneStampClassification {
	
	PARENT(0,"育児"),
	
	NURSE(1,"介護"),
	
	BREAK(2,"休憩");
	
	public int value;
	
	public String name;
}
