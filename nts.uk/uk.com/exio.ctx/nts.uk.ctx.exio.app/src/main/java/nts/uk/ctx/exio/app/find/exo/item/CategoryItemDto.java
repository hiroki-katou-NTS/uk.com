package nts.uk.ctx.exio.app.find.exo.item;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;

@Value
public class CategoryItemDto {

	/**
	 * カテゴリ項目NO
	 */
	private int categoryItemNo;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * 演算符号
	 */
	private int operationSymbol;

	public CategoryItemDto(int categoryItemNo, int categoryId, int operationSymbol) {
		super();
		this.categoryItemNo = categoryItemNo;
		this.categoryId = categoryId;
		this.operationSymbol = operationSymbol;
	}

	public static CategoryItemDto fromDomain(CategoryItem domain) {
		return new CategoryItemDto(domain.getCategoryItemNo().v(), domain.getCategoryId().v(),
				domain.getOperationSymbol().value);
	}
}
