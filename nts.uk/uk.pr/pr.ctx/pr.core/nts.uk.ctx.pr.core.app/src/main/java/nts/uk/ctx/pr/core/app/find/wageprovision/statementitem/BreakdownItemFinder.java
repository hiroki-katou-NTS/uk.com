package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author thanh.tq 
 *
 */
@Stateless
public class BreakdownItemFinder {

	@Inject
	private BreakdownItemSetRepository finder;

	public List<BreakdownItemSetDto> getBreakdownItemStById(int categoryAtr, String itemNameCd) {
		String cid = AppContexts.user().companyId();

		return finder.getBreakdownItemStByStatementItemId(cid, categoryAtr, itemNameCd).stream()
				.map(i -> BreakdownItemSetDto.fromDomain(i)).collect(Collectors.toList());
	}

}
