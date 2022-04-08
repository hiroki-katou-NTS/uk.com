package nts.uk.ctx.at.record.dom.adapter.jobtitle;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.AllMasterAttItem.MasterAttItemDetail;

/**
 * 
 * @author sonnh1
 *
 */
public interface RecJobTitleAdapter {

	Map<Pair<String, GeneralDate>, Pair<String,String>> getJobTitleMapIdBaseDateName(String companyId, List<String> jobIds, List<GeneralDate> baseDates);
	
	List<MasterAttItemDetail> findAll(String companyId, GeneralDate baseDate);
}
