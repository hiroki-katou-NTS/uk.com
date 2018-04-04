/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.temporarywork;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Interface ManageWorkTemporaryGetMemento.
 *
 * @author hoangdd
 */
public interface ManageWorkTemporaryGetMemento {
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	String getCompanyID();
	
	/**
	 * Gets the max usage.
	 *
	 * @return the max usage
	 */
	MaxUsage getMaxUsage();
	
	/**
	 * Gets the time treat temporary same.
	 *
	 * @return the time treat temporary same
	 */
	AttendanceTime getTimeTreatTemporarySame();
}

