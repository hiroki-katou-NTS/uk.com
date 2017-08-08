package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;
import java.util.Optional;

public interface PerInfoCategoryRepositoty {

	List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd);

	Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd);
	
    void addPerInfoCtgRoot(PersonInfoCategory perInfoCtg, String contractCd);
    
    void addPerInfoCtgWithListCompany(List<PersonInfoCategory> perInfoCtgList, String contractCd);
    
    void updatePerInfoCtg(PersonInfoCategory perInfoCtg, String contractCd);
    
    String getPerInfoCtgCodeLastest(PersonInfoCategory perInfoCtg, String contractCd);
}
