package nts.uk.ctx.at.request.pub.aplicationreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProcessStateReflectExport {
	/**
	 *  中断 */
	INTERRUPTION(0),

	/**
	 *  正常終了 */
	SUCCESS(1);

	public final int value;
}
