package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;
import java.util.Optional;

public interface PerInfoCtgRepositoty{
	Optional<PersonInfoCategory> getDetailCategoryInfo(String companyId, String categoryId,String contractCd);
	List<String> getItemInfoId(String categoryId, String contractCd);
	void update(PersonInfoCategory domain);
}
