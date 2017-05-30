package workplace;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author hieult
 *
 */
public interface WorkPlaceRepository {
	
	/**
	 * Find All
	 * @param companyID
	 * @return List Work Place
	 */
	List<WorkPlace> findAll (String companyID);
	/**
	 * 
	 * @param companyID
	 * @param workLocationCD
	 * @return Optional Work Place
	 */
	Optional<WorkPlace> findByCode (String companyID, String workLocationCD); 
}
