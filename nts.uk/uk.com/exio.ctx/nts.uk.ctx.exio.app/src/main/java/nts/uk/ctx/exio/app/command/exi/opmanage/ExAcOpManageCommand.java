package nts.uk.ctx.exio.app.command.exi.opmanage;

import lombok.Value;

@Value
public class ExAcOpManageCommand {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部受入処理ID
	 */
	private String processId;

	/**
	 * エラー件数
	 */
	private int errorCount;

	/**
	 * 中断するしない
	 */
	private int interruption;

	/**
	 * 処理カウント
	 */
	private int processCount;

	/**
	 * 処理トータルカウント
	 */
	private int processTotalCount;

	/**
	 * 動作状態
	 */
	private int stateBehavior;

	private Long version;

}
