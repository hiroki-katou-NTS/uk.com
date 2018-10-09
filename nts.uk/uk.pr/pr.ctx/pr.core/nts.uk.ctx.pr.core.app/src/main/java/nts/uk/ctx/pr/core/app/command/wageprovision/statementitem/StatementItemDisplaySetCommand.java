package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class StatementItemDisplaySetCommand {

	/**
	 * ゼロ表示区分
	 */
	private int zeroDisplayAtr;

	/**
	 * 項目名表示
	 */
	private Integer itemNameDisplay;

}
