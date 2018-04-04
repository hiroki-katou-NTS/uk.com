/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.temporarywork;

/**
 * The Interface ManageWorkTemporarySetMemento.
 *
 * @author hoangdd
 */
public interface ManageWorkTemporarySetMemento {
	
	/**
	 * Sets the company ID.
	 *
	 * @param companyID the new company ID
	 */
	void setCompanyID(String companyID);
	
	/**
	 * Sets the max usage.
	 *
	 * @param maxUsage the new max usage
	 */
	void setMaxUsage(int maxUsage);
	
	/**
	 * Sets the time treat temporary same.
	 *
	 * @param time the new time treat temporary same
	 */
	void setTimeTreatTemporarySame(int time);
}

