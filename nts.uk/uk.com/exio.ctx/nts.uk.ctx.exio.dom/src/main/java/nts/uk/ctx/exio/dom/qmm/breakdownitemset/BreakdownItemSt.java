package nts.uk.ctx.exio.dom.qmm.breakdownitemset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author thanh.tq 内訳項目設定
 *
 */
@Getter
public class BreakdownItemSt extends AggregateRoot {

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 内訳項目コード
	 */
	private BreakdownItemCode breakdownItemCode;

	/**
	 * 内訳項目名称
	 */
	private BreakdownItemName breakdownItemName;

	public BreakdownItemSt(String salaryItemId, int breakdownItemCode, String breakdownItemName) {
		super();
		this.salaryItemId = salaryItemId;
		this.breakdownItemCode = new BreakdownItemCode(breakdownItemCode);
		this.breakdownItemName = new BreakdownItemName(breakdownItemName);
	}

}
