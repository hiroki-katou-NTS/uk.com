package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Value;

@Value
public class CopyStdAcceptCondSetCommand {
	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 外部受入条件コード
	 */
	private String sourceCondSetCode;

	/**
	 * 外部受入条件コード
	 */
	private String destCondSetCode;

	/**
	 * 外部受入条件名称
	 */
	private String destCondSetName;

	private boolean override;
}
