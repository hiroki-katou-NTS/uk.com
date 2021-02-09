package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExtResultStatus {
	/**
	 * 0: 成功
	 */
	SUCCESS(0),
	/**
	 * 1: 中断
	 */
	BREAK(1),
	/**
	 * 2:失敗
	 */
	FAILURE(2);
	public final Integer value;
}
