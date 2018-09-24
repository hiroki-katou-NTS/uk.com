package nts.uk.ctx.at.schedule.dom.adapter.workplace;

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
	
	Optional<SWkpHistImported> findBySid(String employeeId, GeneralDate baseDate);
	
	Map<Pair<String, GeneralDate>, Pair<String,String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates);
}
