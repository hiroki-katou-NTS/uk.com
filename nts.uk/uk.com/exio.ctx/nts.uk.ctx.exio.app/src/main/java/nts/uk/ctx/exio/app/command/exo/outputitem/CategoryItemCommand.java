package nts.uk.ctx.exio.app.command.exo.outputitem;

import lombok.Value;

@Value
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
}
