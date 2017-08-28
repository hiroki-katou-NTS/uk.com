package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;
import java.util.Optional;

public interface PerInfoCtgByCompanyRepositoty{
	Optional<PersonInfoCategory> getDetailCategoryInfo(String companyId, String categoryId,String contractCd);
	List<String> getItemInfoId(String categoryId, String contractCd);
	void addPerCtgOrder(PersonInfoCtgOrder domain);
	void update(PersonInfoCategory domain);
	void deleteByCompanyId(String companyId);
	List<String> checkCtgNameIsUnique(String companyId, String newCtgName); 
}
