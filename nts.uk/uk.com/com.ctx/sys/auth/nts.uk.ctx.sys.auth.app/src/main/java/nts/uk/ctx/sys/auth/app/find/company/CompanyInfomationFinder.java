package nts.uk.ctx.sys.auth.app.find.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;

@Stateless
public class CompanyInfomationFinder {

	@Inject
	private CompanyAdapter companyAdapter;

	public List<CompanyDto> findAllCompany(){
		List<CompanyImport> listAllCompany = companyAdapter.findAllCompany();
		List<CompanyDto> listAllCompanyDTO = new ArrayList<CompanyDto>();
		listAllCompany.stream().map(c -> {
			return listAllCompanyDTO.add(new CompanyDto(c.getCompanyCode(), c.getCompanyName(), c.getCompanyId()));
		}).collect(Collectors.toList());
		return listAllCompanyDTO;
	}

}
