package nts.uk.ctx.at.function.ac.stopbycompany;

import nts.uk.ctx.at.function.dom.adapter.stopbycompany.StopByCompanyAdapter;

/**
 * The Class StopByCompanyAdapterImpl.
 */
//@Stateless
//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StopByCompanyAdapterImpl implements StopByCompanyAdapter {

	/** The pub. */
//	@Inject
//	private StopByCompanyPub pub;
	
	@Override
	public void checkUsageStop(String contractCD, String companyCD) {
//	public UsageStopOutputImport checkUsageStop(String contractCD, String companyCD) {
		// TODO QA111645
//		return this.pub.checkUsageStop(contractCD, companyCD);
	}

}
