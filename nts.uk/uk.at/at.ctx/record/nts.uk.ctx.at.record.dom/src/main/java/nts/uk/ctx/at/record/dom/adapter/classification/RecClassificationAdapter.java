package nts.uk.ctx.at.record.dom.adapter.classification;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 * @author sonnh1
 *
 */
public interface RecClassificationAdapter {

	Map<String, String> getClassificationMapCodeName(String companyId, List<String> clsCds);
	
	//get code, name master 
	List<Pair<String, String>> getClassificationByCompanyId(String companyId);
}
