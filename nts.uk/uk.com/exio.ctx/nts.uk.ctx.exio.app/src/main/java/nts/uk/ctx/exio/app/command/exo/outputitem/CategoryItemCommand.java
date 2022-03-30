package nts.uk.ctx.exio.app.command.exo.outputitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@NoArgsConstructor
@Setter
@Getter
public class CategoryItemCommand {
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
	private Integer operationSymbol;
	
	/**
	 * 順序
	 */
	private int displayOrder;

	public CategoryItemCommand(int categoryItemNo, int categoryId, Integer operationSymbol, int displayOrder) {
		super();
		this.categoryItemNo = categoryItemNo;
		this.categoryId = categoryId;
		this.operationSymbol = operationSymbol;
		this.displayOrder = displayOrder;
	}
	
}
