package nts.uk.ctx.at.schedule.dom.adapter.jobtitle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface SyJobTitleAdapter {
	/**
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<EmployeeJobHistImported> findBySid(String employeeId, GeneralDate baseDate);
	
	Map<Pair<String, GeneralDate>, Pair<String,String>> getJobTitleMapIdBaseDateName(String companyId, List<String> jobIds, List<GeneralDate> baseDates);
}
