package nts.uk.ctx.at.schedule.dom.shift.specificdayset.company;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CompanySpecificDateRepository {
	//get List Company Specific Date by Date
	List<CompanySpecificDateItem> getComSpecByDate(String companyId, GeneralDate specificDate);

	//get List Company Specific Date by Date WITH NAME
	List<CompanySpecificDateItem> getComSpecByDateWithName(String companyId, GeneralDate startDate, GeneralDate endDate);
	
	//insert to Company Specific Date 
	void InsertComSpecDate(List<CompanySpecificDateItem> lstComSpecDateItem);
	
	//delete to Company Specific Date 
	void DeleteComSpecDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	/**
	 * add List ComSpecDate
	 * @param lstComSpecDateItem
	 */
	void addListComSpecDate(List<CompanySpecificDateItem> lstComSpecDateItem);
	/**
	 * delete ComSpecByDate
	 * @param companyId
	 * @param specificDate
	 */
	void deleteComSpecByDate(String companyId, GeneralDate specificDate);
}
