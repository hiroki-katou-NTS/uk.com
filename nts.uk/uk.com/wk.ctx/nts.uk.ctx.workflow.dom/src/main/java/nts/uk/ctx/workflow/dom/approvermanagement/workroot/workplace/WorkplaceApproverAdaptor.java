/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
<<<<<<< HEAD
 * 
 * @author dudt
 *
=======
 * The Interface WorkplaceApproverAdaptor.
>>>>>>> pj/at/develop
 */
// 基準日時点の対象の職場IDに一致する職場コードと職場名称を取得する
public interface WorkplaceApproverAdaptor {

	/**
	 * Find by wkp id.
	 *
	 * @param workplaceId the workplace id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<WorkplaceApproverDto> findByWkpId(String workplaceId, GeneralDate baseDate);
}
