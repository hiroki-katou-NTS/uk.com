package nts.uk.screen.at.app.shift.specificdayset.company;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;

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
	List<GeneralDate> findDataComSpecificDateSet(String companyId, WorkplaceIdAndDateScreenParams params);
}
