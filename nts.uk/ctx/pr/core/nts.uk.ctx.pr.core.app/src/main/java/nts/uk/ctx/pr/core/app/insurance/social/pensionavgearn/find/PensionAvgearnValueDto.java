package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

@Builder
@Getter
public class PensionAvgearnValueDto {
	/** The male amount. */
	private BigDecimal maleAmount;

	/** The female amount. */
	private BigDecimal femaleAmount;

	/** The unknown amount. */
	private BigDecimal unknownAmount;

	public static PensionAvgearnValueDto fromDomain(PensionAvgearnValue domain) {
		return new PensionAvgearnValueDto(domain.getMaleAmount().v(), domain.getFemaleAmount().v(),
				domain.getUnknownAmount().v());
	}
}
