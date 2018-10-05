package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;

@Value
public class TaxExemptionLimitDto {

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
	private long taxExemption;

	public static TaxExemptionLimitDto fromDomain(TaxExemptionLimit domain) {
		return new TaxExemptionLimitDto(domain.getTaxFreeAmountCode().v(), domain.getTaxExemptionName().v(),
				domain.getTaxExemption().v());
	}
}
