package nts.uk.ctx.at.schedule.dom.shift.specificdayset.company;

import java.util.List;

public interface CompanySpecificDateRepository {
	//get List Company Specific Date by Date
	List<CompanySpecificDateItem> getComSpecByDate(String companyId, int specificDate);

	//get List Company Specific Date by Date WITH NAME
	List<CompanySpecificDateItem> getComSpecByDateWithName(String companyId, String specificDate, int useAtr);
	
	//insert to Company Specific Date 
	void InsertComSpecDate(List<CompanySpecificDateItem> lstComSpecDateItem);
	
	//delete to Company Specific Date 
	void DeleteComSpecDate(String companyId,String processMonth);
}
