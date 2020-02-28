package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class BreakdownItemSetCommand {

	/**
	 * カテゴリ区分
	 */
	private int categoryAtr;

	/**
	 * 項目名コード
	 */
	private String itemNameCd;

	/**
	 * 内訳項目コード
	 */
	private String breakdownItemCode;

	/**
	 * 内訳項目名称
	 */
	private String breakdownItemName;
	
}
