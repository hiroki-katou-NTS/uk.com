package nts.uk.ctx.pereg.ac.company;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;

@Stateless
public class CompanyAnticorrupt implements ICompanyRepo{
	
	@Inject
	private ICompanyPub companyPublish;

	@Override
	public List<String> acquireAllCompany() {
		return companyPublish.acquireAllCompany();
	}

}
