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
		//11.初期データ取得処理 11. Initial data acquisition processing [住民税納付先マスタ.SEL-1] 
//		if(allResidential.isEmpty()){
//			companyCode="0000";
//			allResidential = this.resiTaxRepository.getAllResidentialTax(companyCode).stream().map(c -> ResidentialTaxDto.fromDomain(c))
//					.collect(Collectors.toList());
//		}
		return allResidential;
	}
	// SEL2 get data by companyCode, resiTaxCode, resiTaxReportCode, purpose: check obj is not use or use in order to delete
	public Optional<ResidentialTaxDto> getAllResidialTax(String resiTaxCode,  String  resiTaxReportCode){
		String companyCode= AppContexts.user().companyCode();
		return this.resiTaxRepository.getAllResidialTax(companyCode, resiTaxCode, resiTaxReportCode).map(c -> ResidentialTaxDto.fromDomain(c));
	}

}
