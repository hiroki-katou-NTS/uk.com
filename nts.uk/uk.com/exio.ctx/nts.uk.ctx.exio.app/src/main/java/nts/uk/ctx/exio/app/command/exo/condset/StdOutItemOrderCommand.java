package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

	public StdOutItemOrderCommand(String cid, String outItemCd, String condSetCd, int order) {
		super();
		this.cid = cid;
		this.outItemCd = outItemCd;
		this.condSetCd = condSetCd;
		this.order = order;
	}
	
}
