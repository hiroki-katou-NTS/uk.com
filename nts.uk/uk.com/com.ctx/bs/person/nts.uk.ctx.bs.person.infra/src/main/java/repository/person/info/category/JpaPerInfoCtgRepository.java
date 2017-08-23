package repository.person.info.category;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCtgRepository;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
@Stateless
public class JpaPerInfoCtgRepository implements PersonInfoCtgRepository{
	

	private final static String SELECT_ITEMS_INFO_QUERY = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId, i.itemName,i.abolitionAtr, io.disporder FROM PpemtPerInfoItem i"
			+ " INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd"
			+ " AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd INNER JOIN PpemtPerInfoItemOrder io"
			+ " ON io.ppemtPerInfoItemPK.perInfoItemDefId = i.ppemtPerInfoItemPK.perInfoItemDefId AND io.perInfoCtgId = i.perInfoCtgId"
			+ " WHERE ic.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.perInfoCtgId = :perInfoCtgId AND ic.itemParentCd IS NULL "
			+ " ORDER BY io.disporder";

	@Override
	public List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPerInfoCtgRoot(PersonInfoCategory perInfoCtg, String contractCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPerInfoCtgWithListCompany(PersonInfoCategory perInfoCtg, String contractCd,
			List<String> companyIdList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePerInfoCtg(PersonInfoCategory perInfoCtg, String contractCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePerInfoCtgWithListCompany(PersonInfoCategory perInfoCtg, String contractCd,
			List<String> companyIdList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPerInfoCtgCodeLastest(String contractCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkCtgNameIsUnique(String companyId, String newCtgName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getPerInfoCtgIdList(List<String> companyIdList, String categoryCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDateRangeItemRoot(DateRangeItem dateRangeItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListDateRangeItem(List<DateRangeItem> dateRangeItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PersonInfoCategory> getAllCategoryInfo(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
