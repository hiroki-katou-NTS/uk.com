package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ValidityPeriodAndCycleSetFinder {
	@Inject
	private SetPeriodCycleRepository finder;

	public ValidityPeriodAndCycleSetDto getAllSetValidityPeriodCycle(int categoryAtr, String itemNameCd) {
		String cid = AppContexts.user().companyId();

		return finder.getSetPeriodCycleById(cid, categoryAtr, itemNameCd).map(item -> ValidityPeriodAndCycleSetDto.fromDomain(item))
				.orElse(null);
	}
}
