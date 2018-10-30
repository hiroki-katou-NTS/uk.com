package nts.uk.ctx.at.record.dom.worklocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * 
 * @author hieult
 *
 */
public interface WorkLocationRepository {
	
	/**
	 * Find All
	 * @param companyID
	 * @return List Work Place
	 */
	List<WorkLocation> findAll (String companyID);
	/**
	 * 
	 * @param companyID
	 * @param workLocationCD
	 * @return Optional Work Place
	 */
	Optional<WorkLocation> findByCode (String companyID, String workLocationCD); 
	
	Map<String, String> getNameByCode(String companyId, List<String> listWorkLocationCd);
}
