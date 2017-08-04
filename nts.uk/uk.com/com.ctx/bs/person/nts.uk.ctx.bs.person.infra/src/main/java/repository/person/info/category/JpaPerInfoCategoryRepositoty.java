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
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid";

	private final static String SELECT_CATEGORY_BY_CATEGORY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd"
			+ " AND ca.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId";

	@Override
	public List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd) {

		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("cid", companyId).getList(c -> {
					String personInfoCategoryId = String.valueOf(c[0]);
					String categoryCode = String.valueOf(c[1]);
					String categoryName = String.valueOf(c[2]);
					int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
					String categoryParentCd = String.valueOf(c[4]);
					int categoryType = Integer.parseInt(String.valueOf(c[5]));
					int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
					int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
					return PersonInfoCategory.createFromEntity(personInfoCategoryId, companyId, categoryCode,
							categoryParentCd, categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr);

				});
	}

	@Override
	public Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("perInfoCtgId", perInfoCategoryId).getSingle(c -> {
					String personInfoCategoryId = String.valueOf(c[0]);
					String categoryCode = String.valueOf(c[1]);
					String categoryName = String.valueOf(c[2]);
					int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
					String categoryParentCd = String.valueOf(c[4]);
					int categoryType = Integer.parseInt(String.valueOf(c[5]));
					int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
					int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
					return PersonInfoCategory.createFromEntity(personInfoCategoryId, null, categoryCode,
							categoryParentCd, categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr);
				});
	}

}
