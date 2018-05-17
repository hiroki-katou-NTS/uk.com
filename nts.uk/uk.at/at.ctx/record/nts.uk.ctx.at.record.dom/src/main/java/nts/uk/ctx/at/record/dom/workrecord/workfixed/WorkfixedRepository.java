/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.workfixed;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

/**
 * The Interface WorkfixedRepository.
 */
public interface WorkfixedRepository {

	/**
 	 * Removes the.
 	 *
 	 * @param workPlaceId the work place id
 	 * @param closureId the closure id
 	 * @param cid the cid
 	 */
 	void remove(String workPlaceId, Integer closureId, String cid);

	/**
 	 * Adds the.
 	 *
 	 * @param workFixed the work fixed
 	 */
 	void add(WorkFixed workFixed);

	/**
 	 * Find by work place id and closure id.
 	 *
 	 * @param workPlaceId the work place id
 	 * @param closureId the closure id
 	 * @param cid the cid
 	 * @return the optional
 	 */
 	Optional<WorkFixed> findByWorkPlaceIdAndClosureId(String workPlaceId, Integer closureId, String cid);

	/**
 	 * Update.
 	 *
 	 * @param workFixed the work fixed
 	 */
 	void update(WorkFixed workFixed);

	/**
	  * Find work fixed.
	  *
	  * @param cid the cid
	  * @return the list
	  */
	List<WorkFixed> findWorkFixed(String cid);
	
	/**
	 * Find.
	 *
	 * @param cid the cid
	 * @param processYm the process ym
	 * @param wkpId the wkp id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<WorkFixed> find(String cid, String wkpId, Integer closureId, YearMonth processYm);
}
