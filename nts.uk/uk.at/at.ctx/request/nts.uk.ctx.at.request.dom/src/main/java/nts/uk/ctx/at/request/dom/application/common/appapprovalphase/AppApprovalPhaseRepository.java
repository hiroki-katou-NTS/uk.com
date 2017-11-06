package nts.uk.ctx.at.request.dom.application.common.appapprovalphase;
/**
 * 
 * @author hieult
 *
 */

import java.util.List;
import java.util.Optional;

public interface AppApprovalPhaseRepository {
	/**
	 * Find Optional
	 * 
	 * @param companyID
	 * @param appID
	 * @param phaseID
	 * @return
	 */
	Optional<AppApprovalPhase> findByCode(String companyID, String appID, String phaseID);

	
	
	// 承認情報の整理
	/**
	 * Find List 5 Phase
	 * 
	 * @param companyID
	 * @param appID
	 * @return
	 */
	List<AppApprovalPhase> findPhaseByAppID(String companyID, String appID);

	/**
	 * Create
	 * 
	 * @param appAprovalPhase
	 */
	void create(AppApprovalPhase appApprovalPhase);

	/**
	 * Update
	 * 
	 * @param appAprovalPhase
	 */
	void update(AppApprovalPhase appAprovalPhase);

	/**
	 * Delete by phaseID
	 * 
	 * @param companyID
	 * @param appID
	 * @param phaseID
	 */
	void delete(AppApprovalPhase appAprovalPhase);

}
