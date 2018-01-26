package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;

/**
 * 全員作成するしない区分
 */
@AllArgsConstructor
public enum TargetClassification {
	/* 全員 */
	ALL(0),
	
	/* 条件を指定して作成 */
	CONDITIONS(1);
	
	/** The value. */
	public final int value;
}
