package nts.uk.ctx.exio.dom.exo.outitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author son.tc
 *
 */
@AllArgsConstructor
@Getter
public class CtgItem extends AggregateRoot {
	/**
	 * カテゴリ項目NO
	 */
	private CtgItemNo ctgItemNo;

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
	 * カテゴリID
	 */
	private CtgId ctgId;

	/**
	 * 演算符号
	 */
	private OperationSymbol operationSymbol;

	/**
	 * 順序
	 */
	private int order;

}
