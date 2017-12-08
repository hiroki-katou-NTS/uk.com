package nts.uk.screen.at.app.shift.specificdayset.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

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
	List<GeneralDate> findDataWkpSpecificDateSet(WorkplaceIdAndDateScreenParams params);
}
