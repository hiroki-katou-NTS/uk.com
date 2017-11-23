package nts.uk.screen.at.app.shift.specificdayset.company;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public interface ComSpecificDateSetScreenRepository {
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<BigDecimal> findDataComSpecificDateSet(String companyId, StartDateEndDateScreenParams params);
}
