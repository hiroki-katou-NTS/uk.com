package nts.uk.ctx.pr.core.app.find.rule.law.tax.residential;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * 
 * @author lanlt
 *
 */
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ResidentialTaxFinder {
	@Inject
	private ResidentialTaxRepository resiTaxRepository;
	// SEL1 get data by companyCode login
	public List<ResidentialTaxDto> getAllResidentialTax(){
		String companyCode= AppContexts.user().companyCode();
		List<ResidentialTaxDto> allResidential = this.resiTaxRepository.getAllResidentialTax(companyCode).stream().map(c -> ResidentialTaxDto.fromDomain(c))
		.collect(Collectors.toList());
		return allResidential;
	}
	// SEL_1 where CCD= 0000
	public List<ResidentialTaxDto> getAllResidentialTax(String companyCode){
		List<ResidentialTaxDto> allResidential = this.resiTaxRepository.getAllResidentialTax(companyCode).stream().map(c -> ResidentialTaxDto.fromDomain(c))
		.collect(Collectors.toList());
		return allResidential;
	}
	// SEL2 get data by companyCode, resiTaxCode, resiTaxReportCode, purpose: check obj is not use or use in order to delete
	public List<ResidentialTaxDto> getAllResidentialTax(String resiTaxCode,  String  resiTaxReportCode){
		String companyCode= AppContexts.user().companyCode();
		List<ResidentialTaxDto> allResidential = this.resiTaxRepository.getAllResidentialTax(companyCode, resiTaxCode, resiTaxReportCode)
				.stream()
				.map(c -> ResidentialTaxDto.fromDomain(c))
				.collect(Collectors.toList());
		return allResidential;
	}
	//SEL3
	public Optional<ResidentialTaxDto> getResidentialTax(String resiTaxCode){
		String companyCode = "";
        if(AppContexts.user() !=null){
        	companyCode = AppContexts.user().companyCode();
        	return this.resiTaxRepository.getResidentialTax(companyCode, resiTaxCode).map(c -> ResidentialTaxDto.fromDomain(c));
        }else{
        	return null;
        }		
		
	}

}
