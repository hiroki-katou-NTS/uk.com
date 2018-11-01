package nts.uk.ctx.at.record.dom.adapter.jobtitle;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface RecJobTitleAdapter {

	Map<Pair<String, GeneralDate>, Pair<String,String>> getJobTitleMapIdBaseDateName(String companyId, List<String> jobIds, List<GeneralDate> baseDates);
}
