package nts.uk.ctx.sys.portal.ac.find.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.pub.company.CompanyExport;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.sys.gateway.pub.authmana.ListCompanySwitchablePub;
import nts.uk.ctx.sys.portal.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.company.CompanyDto;

@Stateless
public class CompanyPortalAdapterImpl implements CompanyAdapter {

	@Inject
	private ICompanyPub companyPub;
	
	@Inject
	private ListCompanySwitchablePub lstComSwitchablePub;
	
	@Override
	public List<String> getCompanyIdList(String userId, String contractCd) {
		return lstComSwitchablePub.getCompanyList(userId, contractCd);
	}
	
	@Override
	public Optional<CompanyDto> getCompany(String companyId) {
		CompanyExport company = companyPub.getCompanyByCid(companyId);
		if (company.getCompanyId() == null) return Optional.empty();
		return Optional.of(new CompanyDto(company.getCompanyCode(), company.getCompanyName(),
				company.getCompanyId(), company.getIsAbolition()));
	}
}
