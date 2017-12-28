package nts.uk.ctx.sys.portal.ac.find.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.pub.company.CompanyExport;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.sys.auth.pub.service.ListCompanyService;
import nts.uk.ctx.sys.portal.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.company.CompanyDto;

@Stateless
public class CompanyPortalAdapterImpl implements CompanyAdapter {

	@Inject
	private ListCompanyService companyService;
	
	@Inject
	private ICompanyPub companyPub;
	
	@Override
	public List<String> getCompanyIdList(String userId, String personId) {
		return companyService.getListCompanyId(userId, personId);
	}
	
	@Override
	public Optional<CompanyDto> getCompany(String companyId) {
		CompanyExport company = companyPub.getCompanyByCid(companyId);
		if (company.getCompanyId() == null) return Optional.empty();
		return Optional.of(new CompanyDto(company.getCompanyCode(), company.getCompanyName(),
				company.getCompanyId(), company.getIsAbolition()));
	}
}
