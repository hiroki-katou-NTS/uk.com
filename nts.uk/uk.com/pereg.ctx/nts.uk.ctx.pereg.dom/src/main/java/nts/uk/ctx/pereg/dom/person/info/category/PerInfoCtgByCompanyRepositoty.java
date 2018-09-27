package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface PerInfoCtgByCompanyRepositoty{

	Optional<PersonInfoCategory> getDetailCategoryInfo(String companyId, String categoryId,String contractCd);
	
	String getNameCategoryInfo(String companyId, String categoryCd);
	
	List<String> getItemInfoId(String categoryId, String contractCd);
	
	void addPerCtgOrder(PersonInfoCtgOrder domain);
	
	void updatePerCtgOrder(List<PersonInfoCtgOrder> domainList);
	
	void update(PersonInfoCategory domain);
	
	boolean checkCtgNameIsUnique(String companyId, String newCtgName, String ctgId);
	
	List<PersonInfoCtgOrder> getOrderList(String companyId, String contractCd, int salaryUseAtr,
			int personnelUseAtr, int employmentUseAtr);
	
	HashMap<Integer, HashMap<String, Integer>> getOrderList(List<String> categoryIds, List<String> itemDefinitionIds);
}
