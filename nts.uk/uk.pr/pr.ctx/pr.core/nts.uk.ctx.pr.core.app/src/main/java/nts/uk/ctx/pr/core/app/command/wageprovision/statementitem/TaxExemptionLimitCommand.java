package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class TaxExemptionLimitCommand {

	/**
	 * 非課税限度額コード
	 */
	private String taxFreeamountCode;

	/**
	 * 非課税限度額名称
	 */
	private String taxExemptionName;

	/**
	 * 非課税限度額
	 */
	private int taxExemption;

}
