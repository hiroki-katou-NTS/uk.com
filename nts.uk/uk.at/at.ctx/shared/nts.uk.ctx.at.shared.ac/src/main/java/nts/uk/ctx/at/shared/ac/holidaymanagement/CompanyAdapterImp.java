package nts.uk.ctx.at.shared.ac.holidaymanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.bs.company.pub.company.BeginOfMonthExport;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;

/**
 * The Class CompanyAdapterImp.
 */
@Stateless
public class CompanyAdapterImp implements CompanyAdapter{

	/** The company pub. */
	@Inject ICompanyPub companyPub;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter#getFirstMonth(java.lang.String)
	 */
	@Override
	public CompanyDto getFirstMonth(String companyId) {
		BeginOfMonthExport beginOfMonthExport = companyPub.getBeginOfMonth(companyId);
		return new CompanyDto(beginOfMonthExport.getStartMonth());
	}

}
