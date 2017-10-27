package nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace;

import java.util.List;

public interface WorkplaceSpecificDateRepository {
	// get Company Specific Date by Date
	List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, int specificDate);

	// get List Company Specific Date by Date WITH NAME
	List<WorkplaceSpecificDateItem> getWpSpecByDateWithName(String workplaceId, String specificDate);

	// insert to Company Specific Date
	void InsertWpSpecDate(List<WorkplaceSpecificDateItem> lstWpSpecDateItem);

	// delete to Company Specific Date
	void DeleteWpSpecDate(String workPlaceId, String processMonth);
	/**
	 * delete WorkplaceSpec
	 * @param workplaceId
	 * @param specificDate
	 */
	void deleteWorkplaceSpec(String workplaceId, int specificDate);

}
