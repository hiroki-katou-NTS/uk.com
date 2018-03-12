package nts.uk.ctx.workflow.pub.agent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentAppTypeExport {
	/**
	 *  0:代行者指定
	 */
	SUBSTITUTE_DESIGNATION(0),
	/**
	 *  1:パス
	 */
	PATH(1),
	/**
	 *  2:設定なし
	 */
	NO_SETTINGS(2);
	public final int value;

}
