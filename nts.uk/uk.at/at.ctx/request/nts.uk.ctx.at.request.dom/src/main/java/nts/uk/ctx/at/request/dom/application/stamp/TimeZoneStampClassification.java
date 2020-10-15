package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//打刻申請時間帯分類
public enum TimeZoneStampClassification {
//	2
	PARENT(0,"育児"),
//	6
	NURSE(1,"介護"),
//	5
	BREAK(2,"休憩");
	
	public int value;
	
	public String name;
}
