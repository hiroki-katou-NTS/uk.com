package nts.uk.ctx.at.schedule.dom.shift.specificdayset.company;

import java.util.List;

public interface CompanySpecificDateRepository {
	//get List Company Specific Date by Date
	List<CompanySpecificDateItem> getComSpecByDate(String companyId, int specificDate);
}
