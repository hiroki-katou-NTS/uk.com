package nts.uk.screen.at.app.shift.specificdayset.company;

import java.util.List;

import nts.arc.time.GeneralDate;

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
	List<GeneralDate> findDataComSpecificDateSet(String companyId, StartDateEndDateScreenParams params);
}
