package nts.uk.ctx.at.record.dom.adapter.workplace;

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
public interface SyWorkplaceAdapter {

	Map<String, Pair<String, String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates);
	
	Optional<SWkpHistRcImported> findBySid(String employeeId, GeneralDate baseDate);
	
	List<SWkpHistRcImported> findBySid(List<String>employeeIds, GeneralDate baseDate);
}
