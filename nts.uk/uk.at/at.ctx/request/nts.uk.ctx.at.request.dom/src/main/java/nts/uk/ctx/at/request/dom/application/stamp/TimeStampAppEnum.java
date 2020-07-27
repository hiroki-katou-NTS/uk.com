package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeStampAppEnum {
	
	ATTEENDENCE_OR_RETIREMENT(0,"出勤／退勤"),
	
	EXTRAORDINARY(1,"臨時"),
	
	GOOUT_RETURNING (2,"外出／戻り"),
	
	CHEERING(3,"応援");
	
	public int value;
	
	public String name;
}
