package nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace;

import java.util.List;

public interface WorkplaceSpecificDateRepository {
	// get Company Specific Date by Date
	List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String companyId, int specificDate);

}
