package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimitRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author thanh.tq 非課税限度額の登録
 *
 */
@Stateless
public class TaxExemptLimitFinder {

	@Inject
	private TaxExemptionLimitRepository finder;

	public List<TaxExemptionLimitDto> getTaxExemptLimitByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return finder.getTaxExemptLimitByCompanyId(companyId).stream().map(item -> TaxExemptionLimitDto.fromDomain(item))
				.collect(Collectors.toList());
	}

}
