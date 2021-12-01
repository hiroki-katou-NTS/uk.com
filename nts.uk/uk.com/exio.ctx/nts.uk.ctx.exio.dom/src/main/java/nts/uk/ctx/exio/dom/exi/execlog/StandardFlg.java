package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StandardFlg {
	/**
	 * 0: 定型
	 */
	STANDARD(0),
	/**
	 * 1: ユーザ
	 */
	USER(1);
	public final Integer value;
}
