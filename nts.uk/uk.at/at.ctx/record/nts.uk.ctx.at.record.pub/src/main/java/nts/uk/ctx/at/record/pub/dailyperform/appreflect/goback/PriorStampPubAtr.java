package nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PriorStampPubAtr {
	/**
	 * 実打刻を優先する
	 */
	ACTUAL_TIME(0, "実打刻を優先する"),
	
	/**
	 * 申請の時刻を優先する
	 */
	APP_TIME(1, "申請の時刻を優先する");
	
	public final Integer value;
	
	public final String name;
}
