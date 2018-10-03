package nts.uk.ctx.at.function.dom.adapter.appreflectmanager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProcessStateReflectImport {
	/**
	 *  中断 */
	INTERRUPTION(0),

	/**
	 *  正常終了 */
	SUCCESS(1);

	public final int value;
}
