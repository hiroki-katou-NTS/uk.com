package nts.uk.ctx.pr.core.app.find.rule.law.tax.residential;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * 
 * @author lanlt
 *
 */
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTaxRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ResidentialTaxFinder {
	@Inject
	private ResidentalTaxRepository resiTaxRepository;
	public List<ResidentialTaxDto> getAllResidentialTax(){
		String companyCode= AppContexts.user().companyCode();
		return this.resiTaxRepository.getAllResidentalTax(companyCode).stream().map(c -> ResidentialTaxDto.fromDomain(c))
		.collect(Collectors.toList());
	}

}
