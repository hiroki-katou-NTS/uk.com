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
	 * カテゴリ項目名
	 */
	private String categoryItemName;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * 演算符号
	 */
	private Integer operationSymbol;
	
	/**
	 * 順序
	 */
	private int displayOrder;

	public CategoryItemDto(int itemNo, String categoryItemName, int categoryId, Integer operationSymbol, int displayOrder) {
		super();
		this.categoryItemNo = itemNo;
		this.categoryItemName = categoryItemName;
		this.categoryId = categoryId;
		this.operationSymbol = operationSymbol;
		this.displayOrder = displayOrder;
	}

	public static CategoryItemDto fromDomain(CategoryItem domain) {
		return new CategoryItemDto(domain.getItemNo().v(), "", domain.getCategoryId().v(),
				domain.getOperationSymbol().isPresent() ? domain.getOperationSymbol().get().value : null, domain.getDisplayOrder());
	}

	public static CategoryItemDto fromDomain(CategoryItem domain, String categoryItemName) {
		return new CategoryItemDto(domain.getItemNo().v(), categoryItemName, domain.getCategoryId().v(),
				domain.getOperationSymbol().isPresent() ? domain.getOperationSymbol().get().value : null, domain.getDisplayOrder());
	}
}
