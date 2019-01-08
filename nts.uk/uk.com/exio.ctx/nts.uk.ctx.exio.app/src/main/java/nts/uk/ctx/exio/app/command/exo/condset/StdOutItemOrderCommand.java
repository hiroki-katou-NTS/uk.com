package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.Value;

@Value
public class StdOutItemOrderCommand {
	
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private String outItemCd;

	/**
	 * 条件設定コード
	 */
	private String condSetCd;

	/**
	 * 順序
	 */
	private int order;
}
