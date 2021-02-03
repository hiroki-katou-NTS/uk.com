/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmployeeSpeDeforLaborHourRepository.
 */
public interface DeforLaborTimeShaRepo {

	/**
	 * Find employee spe defor labor by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
	Optional<DeforLaborTimeSha> find(String cid, String empId);
	
	/**
	 * Find by Cid
	 * @param cid
	 * @return
	 */
	List<DeforLaborTimeSha> findDeforLaborTimeShaByCid(String cid);

	/**
	 * Adds the.
	 *
	 * @param empSpeDeforLaborHour the emp spe defor labor hour
	 */
	void add(DeforLaborTimeSha empSpeDeforLaborHour);

	/**
	 * Update.
	 *
	 * @param empSpeDeforLaborHour the emp spe defor labor hour
	 */
	void update(DeforLaborTimeSha empSpeDeforLaborHour);

	/**
	 * Delete.
	 *
	 * @param empSpeDeforLaborHour the emp spe defor labor hour
	 */
	void delete(String cid, String empId);
}
