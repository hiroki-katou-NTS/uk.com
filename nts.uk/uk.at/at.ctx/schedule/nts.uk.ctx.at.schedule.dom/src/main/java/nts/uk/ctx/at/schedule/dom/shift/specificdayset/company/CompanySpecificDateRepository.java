package nts.uk.ctx.at.schedule.dom.shift.specificdayset.company;

import java.util.Optional;

public interface CompanySpecificDateRepository {
	//get Company Specific Date by Date
	Optional<CompanySpecificDateItem> getComSpecByDate(String companyId, int specificDate);
}
