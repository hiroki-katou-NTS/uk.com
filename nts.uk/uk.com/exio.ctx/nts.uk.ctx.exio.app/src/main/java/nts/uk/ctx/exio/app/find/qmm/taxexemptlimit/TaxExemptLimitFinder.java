package nts.uk.ctx.exio.app.find.qmm.taxexemptlimit;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.qmm.taxexemptlimit.TaxExemptLimitRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author thanh.tq 非課税限度額の登録
 *
 */
@Stateless
public class TaxExemptLimitFinder {

	@Inject
	private TaxExemptLimitRepository finder;

	public List<TaxExemptLimitDto> getTaxExemptLimitByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return finder.getTaxExemptLimitByCompanyId(companyId).stream().map(item -> TaxExemptLimitDto.fromDomain(item))
				.collect(Collectors.toList());
	}

}
