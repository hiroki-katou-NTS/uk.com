package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;

public interface PerInfoCategoryRepositoty {

	List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd);

	Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd);

	void addPerInfoCtgRoot(PersonInfoCategory perInfoCtg, String contractCd);

	void addPerInfoCtgWithListCompany(PersonInfoCategory perInfoCtg, String contractCd, List<String> companyIdList);

	void updatePerInfoCtg(PersonInfoCategory perInfoCtg, String contractCd);

	void updatePerInfoCtgWithListCompany(String categoryName, List<String> ctgIdList);

	String getPerInfoCtgCodeLastest(String contractCd);

	boolean checkCtgNameIsUnique(String companyId, String newCtgName);

	List<String> getPerInfoCtgIdList(List<String> companyIdList, String categoryCd);

	void addDateRangeItemRoot(DateRangeItem dateRangeItem);

	void addListDateRangeItem(List<DateRangeItem> dateRangeItems);
}
