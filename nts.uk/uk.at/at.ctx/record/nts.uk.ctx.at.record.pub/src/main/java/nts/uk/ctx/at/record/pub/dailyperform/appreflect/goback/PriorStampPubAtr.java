package nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PriorStampPubAtr {
	/** 直行直帰の打刻優先 */
	GOBACKPRIOR(0, "直行直帰の打刻優先"),
	/**
	 * 実打刻優先
	 */
	REALPRIOR(1, "実打刻優先");
	
	public final Integer value;
	
	public final String name;
}
