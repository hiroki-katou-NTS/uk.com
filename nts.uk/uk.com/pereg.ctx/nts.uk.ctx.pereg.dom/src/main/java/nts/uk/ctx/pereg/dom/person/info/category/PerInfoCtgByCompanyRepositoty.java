package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.List;
import java.util.Optional;

public interface PerInfoCtgByCompanyRepositoty{
	Optional<PersonInfoCategory> getDetailCategoryInfo(String companyId, String categoryId,String contractCd);
	String getNameCategoryInfo(String companyId, String categoryCd);
	List<String> getItemInfoId(String categoryId, String contractCd);
	void addPerCtgOrder(PersonInfoCtgOrder domain);
	void update(PersonInfoCategory domain);
	void deleteByCompanyId(String companyId);
	boolean checkCtgNameIsUnique(String companyId, String newCtgName, String ctgId);
}
