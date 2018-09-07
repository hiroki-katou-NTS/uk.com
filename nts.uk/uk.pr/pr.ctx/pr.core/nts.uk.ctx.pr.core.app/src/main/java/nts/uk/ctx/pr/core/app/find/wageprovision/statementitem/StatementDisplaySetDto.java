package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;

@Value
public class StatementDisplaySetDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * ゼロ表示区分
	 */
	private int zeroDisplayAtr;

	/**
	 * 項目名表示
	 */
	private Integer itemNameDisplay;
	
}
