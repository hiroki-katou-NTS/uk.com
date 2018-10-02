package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemShortName;

/**
 * 
 * @author thanh.tq 内訳項目設定
 *
 */
@Getter
public class BreakdownItemSet extends AggregateRoot {

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
    private ItemShortName breakdownItemName;

	public BreakdownItemSet(String salaryItemId, String breakdownItemCode, String breakdownItemName) {
		super();
		this.salaryItemId = salaryItemId;
		this.breakdownItemCode = new BreakdownItemCode(breakdownItemCode);
		this.breakdownItemName = new ItemShortName(breakdownItemName);
	}

}
