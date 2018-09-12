package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;

/**
 * 
 * @author thanh.tq 
 *
 */
@Stateless
public class BreakdownItemFinder {

	@Inject
	private BreakdownItemSetRepository finder;

	public List<BreakdownItemSetDto> getBreakdownItemStBySalaryId(String salaryItemId) {
		return finder.getBreakdownItemStBySalaryId(salaryItemId).stream()
				.map(i -> BreakdownItemSetDto.fromDomain(i)).collect(Collectors.toList());
	}

}
