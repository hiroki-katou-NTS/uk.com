package nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDate;

public interface WorkplaceSpecificDateRepository {
	/**
	 * 
	 * @param workplaceId
	 * @param specificDate
	 * @return
	 */
	List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, int specificDate);
	/**
	 * hoatt
	 * add WorkplaceSpec
	 * @param lstWorkplaceSpecificDate
	 */
	void addWorkplaceSpec(List<WorkplaceSpecificDateItem> lstWorkplaceSpecificDate);
	/**
	 * hoatt
	 * delete WorkplaceSpec
	 * @param workplaceId
	 * @param specificDate
	 */
	void deleteWorkplaceSpec(String workplaceId, int specificDate);

}
