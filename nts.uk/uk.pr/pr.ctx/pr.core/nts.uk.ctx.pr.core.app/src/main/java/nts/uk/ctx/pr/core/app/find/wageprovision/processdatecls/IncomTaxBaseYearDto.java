package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.IncomTaxBaseYear;

/**
 * 所得税基準年月日
 */
@AllArgsConstructor
@Value
public class IncomTaxBaseYearDto {
	/**
	 * 基準日
	 */
	private int refeDate;

	/**
	 * 基準年
	 */
	private int baseYear;

	/**
	 * 基準月
	 */
	private int baseMonth;

	public static IncomTaxBaseYearDto fromDomain(IncomTaxBaseYear domain) {
		return new IncomTaxBaseYearDto(domain.getInComRefeDate().value, domain.getInComBaseYear().value,
				domain.getInComBaseMonth().value);
	}

}
