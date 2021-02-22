package nts.uk.ctx.at.function.dom.adapter.stopbycompany;

/**
 * The Interface StopBycompanyAdapter.
 */
public interface StopByCompanyAdapter {
	
	/**
	 * Check usage stop.
	 *
	 * @param contractCD the contract CD
	 * @param companyCD the company CD
	 * @return the usage stop output
	 */
	public UsageStopOutputImport checkUsageStop(String contractCD, String companyCD);
}
