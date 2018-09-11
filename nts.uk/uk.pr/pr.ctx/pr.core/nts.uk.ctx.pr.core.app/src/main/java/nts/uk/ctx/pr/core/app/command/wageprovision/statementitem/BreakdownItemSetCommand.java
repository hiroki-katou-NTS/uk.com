package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class BreakdownItemSetCommand {

	/**
	 * 内訳項目コード
	 */
	private int breakdownItemCode;

	/**
	 * 内訳項目名称
	 */
	private String breakdownItemName;
	
}
