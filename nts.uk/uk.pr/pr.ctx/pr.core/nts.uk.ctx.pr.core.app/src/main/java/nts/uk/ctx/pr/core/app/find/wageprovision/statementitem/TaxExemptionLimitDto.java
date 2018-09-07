package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptLimit;

@Value
public class TaxExemptionLimitDto {
	/**
	 * 会社ID
	 */
	private String cid;

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

	public static TaxExemptionLimitDto fromDomain(TaxExemptLimit domain) {
		return new TaxExemptionLimitDto(domain.getCid(), domain.getTaxExemptionName().v(),
				domain.getTaxExemptionName().v(), domain.getTaxExemption().v());
	}
}
