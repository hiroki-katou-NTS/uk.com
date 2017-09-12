package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;

/**
 * 
 * @author hieult
 *
 */
public interface LateOrLeaveEarlyRepository {

	/**
	 * Find by Code
	 * @param companyID
	 * @param appID
	 * @return
	 */
	Optional<LateOrLeaveEarly> findByCode(String companyID, String appID);
	
	/**
	 * Add 
	 * @param lateOrLeaveEarly
	 */
	void add (LateOrLeaveEarly lateOrLeaveEarly);
	
	/**
	 * Update 
	 * @param lateOrLeaveEarly
	 */
	void update (LateOrLeaveEarly lateOrLeaveEarly);
	
	/**
	 * Remove
	 * @param companyID
	 * @param appID
	 */
	void remove (String companyID , String appID);
	/**
	 * Get Reason Temp	
	 * @param companyID
	 * @param applicationType
	 * @return
	 */
 	 AppReason findAppReason(String companyID, ApplicationType applicationType);
}
