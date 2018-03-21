package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PriorStampAtr {
	/** 直行直帰の打刻優先 */
	GOBACKPRIOR(0, "直行直帰の打刻優先"),
	/**
	 * 実打刻優先
	 */
	REALPRIOR(1, "実打刻優先");
	
	public final Integer value;
	
	public final String name;
}
