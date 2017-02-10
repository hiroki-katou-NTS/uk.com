package nts.uk.ctx.basic.app.find.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;
/**
 * 
 * @author lanlt
 *
 */
@Stateless
public class CompanyFinder {
	@Inject
	private CompanyRepository companyRepository;
	public List<CompanyDto> getAllCompanys(){
		return this.companyRepository.getAllCompanys().stream()
				.map(item -> CompanyDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	public Optional<CompanyDto> getCompany(String companyCode){
		return this.companyRepository.getCompanyDetail(companyCode).map(company-> CompanyDto.fromDomain(company));
		
	}
	
	
    
}
