package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProcessingFlg {
	/**
	 * 0: 受入チェック処理
	 */
	CHECKPROCESS(0),
	/**
	 * 1: 受入取込処理
	 */
	CAPTURE(1);
	public final Integer value;
}
