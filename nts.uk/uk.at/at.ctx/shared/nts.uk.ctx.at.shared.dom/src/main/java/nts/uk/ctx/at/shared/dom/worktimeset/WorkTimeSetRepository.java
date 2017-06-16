package nts.uk.ctx.at.shared.dom.worktimeset;

import java.util.List;
import java.util.Optional;

public interface WorkTimeSetRepository {
	
	/**
	 * get Work Time Set by company ID and work time set code
	 * @param companyID company ID
	 * @param workTimeSetCD work time set code
	 * @return Work Time Set
	 */
	public Optional<WorkTimeSet> findByCode(String companyID, String workTimeSetID);
	
	/**
	 * get Work Time Set List by company ID and work time set code list
	 * @param companyID company ID
	 * @param workTimeSetCDs work time set code list
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByCodeList(String companyID, List<String> workTimeSetIDs);
	
	/**
	 * get Work Time Set List by company ID, work time set code list, start day attribute and start time
	 * @param companyID company ID
	 * @param workTimeSetCDs work time set code list
	 * @param startAtr start day attribute
	 * @param startTime start time
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByStart(String companyID, List<String> workTimeSetIDs, int startAtr, int startClock);
	
	/**
	 * get Work Time Set List by company ID, work time set code list, end day attribute and end time
	 * @param companyID company ID
	 * @param workTimeSetCDs work time set code list
	 * @param endAtr end day attribute
	 * @param endTime end time
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByEnd(String companyID, List<String> workTimeSetIDs, int endAtr, int endClock);
	
	/**
	 * get Work Time Set List by company ID, work time set code list, start day attribute, start time, end day attribute and end time
	 * @param companyID company ID
	 * @param workTimeSetCDs work time set code list
	 * @param startAtr start day attribute
	 * @param startTime start time
	 * @param endAtr end day attribute
	 * @param endTime end time
	 * @return Work Time Set List
	 */
	public List<WorkTimeSet> findByStartAndEnd(String companyID, List<String> workTimeSetIDs, int startAtr, int startClock, int endAtr, int endClock);
}
