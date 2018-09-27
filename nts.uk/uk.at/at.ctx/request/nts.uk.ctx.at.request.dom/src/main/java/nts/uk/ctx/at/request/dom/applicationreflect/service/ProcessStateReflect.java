package nts.uk.ctx.at.request.dom.applicationreflect.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProcessStateReflect {
	/**
	 *  中断 */
	INTERRUPTION(0),

	/**
	 *  正常終了 */
	SUCCESS(1);

	public final int value;
}
