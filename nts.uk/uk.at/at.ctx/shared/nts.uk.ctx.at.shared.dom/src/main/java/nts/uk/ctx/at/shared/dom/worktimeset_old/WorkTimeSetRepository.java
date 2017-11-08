package nts.uk.ctx.at.shared.dom.worktimeset_old;

import java.util.List;
import java.util.Optional;

public interface WorkTimeSetRepository {
	
	public List<WorkTimeSet> findByCompanyID(String companyID);
	
	/**
	 * get Work Time Set by company ID and work time set code
	 * @param companyID company ID
	 * @param workTimeSetCD work time set code
	 * @return Work Time Set
	 */
	public Optional<WorkTimeSet> findByCode(String companyID, String siftCD);
	
	/**
	 * get Work Time Set List by company ID and work time set code list
	 * @param companyID company ID
	 * @param workTimeSetCDs work time set code list
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByCodeList(String companyID, List<String> siftCDs);
	
	/**
	 * get Work Time Set List by company ID, work time set code list, start day attribute and start time.
	 *
	 * @param companyID company ID
	 * @param siftCDs the sift C ds
	 * @param startClock the start clock
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByStart(String companyID, List<String> siftCDs, int startClock);
	
	/**
	 * get Work Time Set List by company ID, work time set code list, end day attribute and end time.
	 *
	 * @param companyID company ID
	 * @param siftCDs the sift C ds
	 * @param endClock the end clock
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByEnd(String companyID, List<String> siftCDs, int endClock);
	
	/**
	 * get Work Time Set List by company ID, work time set code list, start day attribute, start time, end day attribute and end time.
	 *
	 * @param companyID company ID
	 * @param siftCDs the sift C ds
	 * @param startClock the start clock
	 * @param endClock the end clock
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByStartAndEnd(String companyID, List<String> siftCDs, int startClock, int endClock);
}
