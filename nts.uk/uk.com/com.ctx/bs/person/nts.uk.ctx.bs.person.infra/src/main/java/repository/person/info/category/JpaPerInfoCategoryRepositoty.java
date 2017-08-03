package repository.person.info.category;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;

@Stateless
public class JpaPerInfoCategoryRepositoty extends JpaRepository implements PerInfoCategoryRepositoty {

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND ca.cid LIKE '%'+ co.ppemtPerInfoCtgCmPK.contractCd" + " AND ca.cid = :cid";

	private final static String SELECT_CATEGORY_BY_CATEGORY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND ca.cid LIKE '%'+ co.ppemtPerInfoCtgCmPK.contractCd"
			+ " AND ca.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId";

	@Override
	public List<PersonInfoCategory> getAllPerInfoCategory(String companyId) {

		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY, Object[].class).setParameter("cid", companyId)
				.getList(c -> {
					String personInfoCategoryId = c[0].toString();
					String categoryCode = c[1].toString();
					String categoryName = c[2].toString();
					int abolitionAtr = Integer.parseInt(c[3].toString());
					String categoryParentCd = c[4].toString();
					int categoryType = Integer.parseInt(c[5].toString());
					int personEmployeeType = Integer.parseInt(c[6].toString());
					int fixedAtr = Integer.parseInt(c[7].toString());
					return PersonInfoCategory.createFromEntity(personInfoCategoryId, companyId, categoryCode,
							categoryParentCd, categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr);

				});
	}

	@Override
	public Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("perInfoCtgId", perInfoCategoryId).getSingle(c -> {
					String personInfoCategoryId = c[0].toString();
					String categoryCode = c[1].toString();
					String categoryName = c[2].toString();
					int abolitionAtr = Integer.parseInt(c[3].toString());
					String categoryParentCd = c[4].toString();
					int categoryType = Integer.parseInt(c[5].toString());
					int personEmployeeType = Integer.parseInt(c[6].toString());
					int fixedAtr = Integer.parseInt(c[7].toString());
					return PersonInfoCategory.createFromEntity(personInfoCategoryId, null, categoryCode,
							categoryParentCd, categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr);
				});
	}
	
	

}
