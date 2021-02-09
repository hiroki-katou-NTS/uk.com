package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExtExecutionMode {
	/**
	 * 0: 自動実行
	 */
	AUTO(0),
	/**
	 * 1: 手動実行
	 */
	MANUAL(1);
	public final Integer value;
}
