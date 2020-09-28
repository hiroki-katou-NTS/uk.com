package nts.uk.ctx.at.function.ac.stopbycompany;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.stopbycompany.StopByCompanyAdapter;
import nts.uk.ctx.at.function.dom.adapter.stopbycompany.UsageStopOutputImport;

/**
 * The Class StopByCompanyAdapterImpl.
 */
public class StopByCompanyAdapterImpl implements StopByCompanyAdapter {

	/** The pub. */
	@Inject
//	private StopByCompanyPub pub;
	
	@Override
	public UsageStopOutputImport checkUsageStop(String contractCD, String companyCD) {
		return null;
		// TODO QA111645
//		return this.pub.checkUsageStop(contractCD, companyCD);
	}

}
