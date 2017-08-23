package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;

public interface PersonInfoCtgRepository extends PerInfoCategoryRepositoty{
   List<PersonInfoCategory> getAllCategoryInfo(String companyId);
   
}
