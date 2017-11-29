package nts.uk.screen.at.app.shift.specificdayset.workplace;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public interface WorkplaceSpecificDateSetScreenRepository {
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<BigDecimal> findDataWkpSpecificDateSet(WorkplaceSpecificDateSetScreenParams params);
}
