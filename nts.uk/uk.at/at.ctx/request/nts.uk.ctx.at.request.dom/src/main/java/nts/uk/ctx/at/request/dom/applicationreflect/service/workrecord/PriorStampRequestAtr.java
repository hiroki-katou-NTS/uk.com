package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PriorStampRequestAtr {
	/** 直行直帰の打刻優先 */
	GOBACKPRIOR(0, "直行直帰の打刻優先"),
	/**
	 * 実打刻優先
	 */
	REALPRIOR(1, "実打刻優先");
	
	public final Integer value;
	
	public final String name;
}
