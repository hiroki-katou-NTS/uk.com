package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class BreakdownItemSetCommand {
	
	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 内訳項目コード
	 */
	private String breakdownItemCode;

	/**
	 * 内訳項目名称
	 */
	private String breakdownItemName;
	
}
