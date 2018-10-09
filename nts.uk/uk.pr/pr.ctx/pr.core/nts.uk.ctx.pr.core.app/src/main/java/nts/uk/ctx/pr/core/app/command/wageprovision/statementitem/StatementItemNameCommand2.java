package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class StatementItemNameCommand2 {
	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 略名
	 */
	private String shortName;

	/**
	 * その他言語名称
	 */
	private String otherLanguageName;

	/**
	 * 英語名称
	 */	
	private String englishName;
}
