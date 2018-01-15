package nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplaceSpecificDateRepository {
	// get Company Specific Date by Date
	List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate);

	// get List Company Specific Date by Date WITH NAME
	List<WorkplaceSpecificDateItem> getWpSpecByDateWithName(String workplaceId, GeneralDate startDate, GeneralDate enDate);

	// insert to Company Specific Date
	void InsertWpSpecDate(List<WorkplaceSpecificDateItem> lstWpSpecDateItem);

	// delete to Company Specific Date
	void DeleteWpSpecDate(String workPlaceId, GeneralDate startDate, GeneralDate endDate);
	/**
	 * delete WorkplaceSpec
	 * @param workplaceId
	 * @param specificDate
	 */
	void deleteWorkplaceSpec(String workplaceId, GeneralDate specificDate);

}
