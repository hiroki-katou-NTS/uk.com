package nts.uk.ctx.exio.dom.exo.outputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * カテゴリ項目
 */
@AllArgsConstructor
@Getter
public class CategoryItem extends DomainObject {

	/**
	 * カテゴリ項目NO
	 */
	private CategoryItemNo categoryItemNo;

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
	private int order;

	public CategoryItem(String categoryItemNo, int categoryId, int operationSymbol, int order) {
		this.categoryItemNo = new CategoryItemNo(categoryItemNo);
		this.categoryId = new CategoryId(categoryId);
		this.operationSymbol = EnumAdaptor.valueOf(operationSymbol, OperationSymbol.class);
		this.order = order;
	}
}
