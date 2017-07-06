package nts.uk.ctx.at.shared.dom.worktime;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface WorkTimeRepository {
	
	public List<WorkTime> findByCompanyID(String companyID);
	
	/**
	 * get Work Time by Work Time Code and Company ID
	 * @param companyID Company ID
	 * @param workTimeCD Work Time Code
	 * @return Work Time
	 */
	public Optional<WorkTime> findByCode(String companyID, String siftCD);
	
	/**
	 * get list Work Time by Work Time Code list and Company ID
	 * @param companyID Company ID
	 * @param workTimeCDs Work Time Code list
	 * @return list Work Time
	 */
	public List<WorkTime> findByCodeList(String companyID, List<String> siftCDs);
	
}
