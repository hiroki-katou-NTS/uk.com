package nts.uk.ctx.sys.auth.ac.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.pub.company.CompanyExport;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;

@Stateless
public class AuthCompanyInforImpl implements CompanyAdapter {

	@Inject
	private ICompanyPub iCompanyPub;
	
	@Override
	public List<CompanyImport> findAllCompany() {
		// Lay tam Request 51
		List<CompanyExport> lstReciveCompany = iCompanyPub.getAllCompany();
		
		List<CompanyImport> listCompany = new ArrayList<>();
		
		lstReciveCompany.stream().map(c -> {
			return listCompany.add(new CompanyImport(c.getCompanyCode(), c.getCompanyName(), c.getCompanyId(), c.getIsAbolition()));
		}).collect(Collectors.toList());
		return listCompany;
	}

}
