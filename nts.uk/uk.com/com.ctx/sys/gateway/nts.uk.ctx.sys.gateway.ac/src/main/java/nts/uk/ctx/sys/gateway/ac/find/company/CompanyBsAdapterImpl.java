package nts.uk.ctx.sys.gateway.ac.find.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.pub.company.CompanyExport;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CompanyBsAdapterImpl implements CompanyBsAdapter{
	
	@Inject
	private ICompanyPub companyPub;
	
	@Override
	public CompanyBsImport getCompanyByCid(String cid) {
		CompanyExport companyExport = companyPub.getCompanyByCid(cid);
		return new CompanyBsImport(
				companyExport.getCompanyCode(), 
				companyExport.getCompanyName(), 
				companyExport.getCompanyId(), 
				companyExport.getIsAbolition());
	}

}
