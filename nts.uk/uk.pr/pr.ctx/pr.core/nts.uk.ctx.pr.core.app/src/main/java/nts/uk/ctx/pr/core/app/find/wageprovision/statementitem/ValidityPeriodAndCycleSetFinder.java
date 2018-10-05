package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;

@Stateless
public class ValidityPeriodAndCycleSetFinder {
	@Inject
	private SetPeriodCycleRepository finder;

	public ValidityPeriodAndCycleSetDto getAllSetValidityPeriodCycle(String salaryItemId) {
		return finder.getSetPeriodCycleById(salaryItemId).map(item -> ValidityPeriodAndCycleSetDto.fromDomain(item))
				.orElse(null);
	}
}
