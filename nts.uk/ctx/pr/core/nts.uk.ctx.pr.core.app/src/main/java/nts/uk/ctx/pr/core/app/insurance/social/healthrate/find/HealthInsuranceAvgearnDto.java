package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceAvgearnValue;

@Builder
@Getter
public class HealthInsuranceAvgearnDto {

	/** The history id. */
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValue companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValue personalAvg;

	/**
	 * From domain.
	 *
	 * @param domain
	 *            the domain
	 * @return the health insurance avgearn dto
	 */
	public static HealthInsuranceAvgearnDto fromDomain(HealthInsuranceAvgearn domain) {
		return new HealthInsuranceAvgearnDto(domain.getHistoryId(), domain.getLevelCode(), domain.getCompanyAvg(),
				domain.getPersonalAvg());
	}
}
