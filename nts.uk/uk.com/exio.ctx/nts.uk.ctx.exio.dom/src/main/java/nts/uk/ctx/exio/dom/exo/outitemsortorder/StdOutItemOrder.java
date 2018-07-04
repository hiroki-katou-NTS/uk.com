package nts.uk.ctx.exio.dom.exo.outitemsortorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 出力項目並び順(定型)
 */
@AllArgsConstructor
@Getter
public class StdOutItemOrder extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private OutItemCd outItemCd;

	/**
	 * 条件設定コード
	 */
	private CondSetCd condSetCd;

	/**
	 * 順序
	 */
	private int order;

	public static StdOutItemOrder createFromJavaType(String cid, OutItemCd outItemCd, CondSetCd condSetCd, int order) {
		StdOutItemOrder stdOutItemOrder = new StdOutItemOrder(cid, outItemCd, condSetCd, order);
		return stdOutItemOrder;
	}
}
