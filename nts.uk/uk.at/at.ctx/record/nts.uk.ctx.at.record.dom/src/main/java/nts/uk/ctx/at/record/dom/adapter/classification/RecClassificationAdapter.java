package nts.uk.ctx.at.record.dom.adapter.classification;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author sonnh1
 *
 */
public interface RecClassificationAdapter {

	Map<String, String> getClassificationMapCodeName(String companyId, List<String> clsCds);
}
