package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemShortName;

/**
 * 
 * @author thanh.tq 内訳項目設定
 *
 */
@Getter
public class BreakdownItemSet extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * カテゴリ区分
     */
    private CategoryAtr categoryAtr;

    /**
     * 項目名コード
     */
    private ItemNameCode itemNameCd;

	/**
	 * 内訳項目コード
	 */
	private BreakdownItemCode breakdownItemCode;

    /**
    * 内訳項目名称
    */
    private ItemShortName breakdownItemName;

	public BreakdownItemSet(String cid, int categoryAtr, String itemNameCd, String breakdownItemCode, String breakdownItemName) {
		super();
        this.cid = cid;
        this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
        this.itemNameCd = new ItemNameCode(itemNameCd);
		this.breakdownItemCode = new BreakdownItemCode(breakdownItemCode);
		this.breakdownItemName = new ItemShortName(breakdownItemName);
	}

}
