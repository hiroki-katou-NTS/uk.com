package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pereg.dom.person.info.category.dto.DateRangeDto;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;

public interface PerInfoCategoryRepositoty {

	List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd, int salaryUseAtr, int personnelUseAtr, int employmentUseAtr);
	
	List<PersonInfoCategory> getAllCategoryForCPS007(String companyId, String contractCd, int forAttendance , int forPayroll, int forPersonnel);

	Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd);

	void addPerInfoCtgRoot(PersonInfoCategory perInfoCtg, String contractCd);

	void addPerInfoCtgWithListCompany(PersonInfoCategory perInfoCtg, String contractCd, List<String> companyIdList);

	void updatePerInfoCtg(PersonInfoCategory perInfoCtg, String contractCd);

	String getPerInfoCtgCodeLastest(String contractCd);

	boolean checkCtgNameIsUnique(String companyId, String newCtgName, String ctgId);

	List<String> getPerInfoCtgIdList(List<String> companyIdList, String categoryCd);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<PersonInfoCategory> getAllPerInfoCtg(String companyId);
	
	List<PersonInfoCategory> getAllCtgWithAuth(String companyId, String roleId, int selfAuth, int otherAuth, boolean isOtherCompany , int forAttendance , int forPayroll , int forPersonnel);
	
	List<PersonInfoCategory> getAllPerInfoCtgUsed(String companyId);

	void addDateRangeItemRoot(DateRangeItem dateRangeItem);

	void addListDateRangeItem(List<DateRangeItem> dateRangeItems);

	// vinhpx: start
	DateRangeItem getDateRangeItemByCtgId(String perInfoCtgId);
	
	List<PersonInfoCategory> getPerInfoCtgByParentCode(String parentCtgCd, String contractCd);
	
	List<PersonInfoCategory> getPerInfoCtgByParentCdWithOrder(String parentCtgCd, String contractCd, String companyId, boolean isASC);

	List<PersonInfoCategory> getPerInfoCategoryByName(String companyId, String contractCd, String name);
	List<PersonInfoCategory> getAllPerInfoCategoryNoMulAndDupHist(String companyId, String contractCd , int forAttendance , int forPayroll, int forPersonnel);
	
	List<PersonInfoCategory> getPerCtgByListCtgCd(List<String> ctgCd, String companyId);
	
	void updateAbolition(List<PersonInfoCategory> ctg, String companyId);
	
	void updateAbolition(List<PersonInfoCategory> ctg);
	// vinhpx: end
	
	//laitv
	Optional<DateRangeItem> getDateRangeItemByCategoryId(String perInfoCtgId);
	
	/**
	 * Get category by category code
	 * @param categoryCD
	 * @param companyID
	 * @return
	 */
	Optional<PersonInfoCategory> getPerInfoCategoryByCtgCD(String categoryCD, String companyID);
	
	
	List<String> getAllCategoryByCtgCD(String categoryCD);

	int getDispOrder(String perInfoCtgId);
	
	String getCatId(String cId, String categoryCode);
	
	List<String> getAllCtgId(List<String> ctgCd, String companyId);
	
	List<DateRangeDto> dateRangeCode();
}
