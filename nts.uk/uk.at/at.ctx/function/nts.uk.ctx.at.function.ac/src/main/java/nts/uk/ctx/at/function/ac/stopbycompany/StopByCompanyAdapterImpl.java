package nts.uk.ctx.at.function.ac.stopbycompany;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.stopbycompany.StopByCompanyAdapter;
import nts.uk.ctx.at.function.dom.adapter.stopbycompany.UsageStopOutputImport;
import nts.uk.ctx.sys.gateway.pub.stopbycompany.StopByCompanyPub;
import nts.uk.ctx.sys.gateway.pub.stopbycompany.UsageStopOutputExport;

/**
 * The Class StopByCompanyAdapterImpl.
 */
@Stateless
public class StopByCompanyAdapterImpl implements StopByCompanyAdapter {

	/** The pub. */
	@Inject
	private StopByCompanyPub pub;
	
	/**
	 * Check usage stop.
	 *
	 * @param contractCD the contract CD
	 * @param companyCD the company CD
	 * @return the usage stop output import
	 */
	@Override
	public UsageStopOutputImport checkUsageStop(String contractCD, String companyCD) {
		UsageStopOutputExport export = this.pub.checkUsageStop(contractCD, companyCD);
		return UsageStopOutputImport.builder()
				.isUsageStop(export.isUsageStop())
				.stopMessage(export.getStopMessage())
				.stopMode(export.getStopMode())
				.build();
	}

}
