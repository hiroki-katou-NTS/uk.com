package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;
/**
 * Refactor4
 * @author hoangnd
 *
 */
//打刻申請時刻分類
@AllArgsConstructor
public enum TimeStampAppEnum {
//	0
	ATTEENDENCE_OR_RETIREMENT(0,"出勤／退勤"),
//	4
	EXTRAORDINARY(1,"臨時"),
//	1	
	GOOUT_RETURNING (2,"外出／戻り"),
//  3	
	CHEERING(3,"応援");
	
	public int value;
	
	public String name;
}
