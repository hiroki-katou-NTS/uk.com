package nts.uk.ctx.exio.dom.exo.outputitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.ItemNo;

/**
 * カテゴリ項目
 */
@Getter
public class CategoryItem extends DomainObject {

	/**
	 * カテゴリ項目NO
	 */
	private ItemNo itemNo;

	/**
	 * カテゴリID
	 */
	private CategoryId categoryId;

	/**
	 * 演算符号
	 */
	private OperationSymbol operationSymbol;

	/**
	 * 順序
	 */
	private int displayOrder;

	public CategoryItem(int itemNo, int categoryId, int operationSymbol, int displayOrder) {
		this.itemNo = new ItemNo(itemNo);
		this.categoryId = new CategoryId(categoryId);
		this.operationSymbol = EnumAdaptor.valueOf(operationSymbol, OperationSymbol.class);
		this.displayOrder = displayOrder;
	}
}
