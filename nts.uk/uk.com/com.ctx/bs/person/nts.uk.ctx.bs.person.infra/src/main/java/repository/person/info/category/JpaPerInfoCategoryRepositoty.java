package repository.person.info.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.info.category.PpemtPerInfoCtg;
import entity.person.info.category.PpemtPerInfoCtgCm;
import entity.person.info.category.PpemtPerInfoCtgCmPK;
import entity.person.info.category.PpemtPerInfoCtgOrder;
import entity.person.info.category.PpemtPerInfoCtgPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;

@Stateless
public class JpaPerInfoCategoryRepositoty extends JpaRepository implements PerInfoCategoryRepositoty {

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,"
			+ " ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder"
			+ " FROM  PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co"
			+ " ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND"
			+ " ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid ORDER BY po.disporder";

	private final static String SELECT_CATEGORY_BY_CATEGORY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd"
			+ " AND ca.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId";

	private final static String SELECT_GET_CATEGORY_CODE_LASTEST_QUERY = "SELECT co.categoryCd PpemtPerInfoCtgCm co"
			+ " WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd ORDER BY co.categoryCd DESC";

	private final static String SELECT_GET_DISPORDER_CTG_OF_COMPANY_QUERY = "SELECT od.disporder PpemtPerInfoCtgOrder od"
			+ " WHERE od.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId AND od.cid = :companyId ORDER BY od.disporder DESC";

	private final static String SELECT_LIST_CTG_ID_QUERY = "SELECT c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " FROM PpemtPerInfoCtg c WHERE c.cid = :companyId AND c.categoryCd = :categoryCd";

	private final static String SELECT_CHECK_CTG_NAME_QUERY = "SELECT c.categoryName"
			+ " FROM PpemtPerInfoCtg c WHERE c.cid = :companyId AND c.categoryName = :categoryName";

	@Override
	public List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("cid", companyId).getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("perInfoCtgId", perInfoCategoryId).getSingle(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public String getPerInfoCtgCodeLastest(String contractCd) {
		List<String> ctgCodeLastest = this.getEntityManager()
				.createQuery(SELECT_GET_CATEGORY_CODE_LASTEST_QUERY, String.class)
				.setParameter("contractCd", contractCd).setMaxResults(1).getResultList();
		if (ctgCodeLastest != null && !ctgCodeLastest.isEmpty()) {
			return ctgCodeLastest.get(0);
		}
		return null;
	}

	@Override
	public List<String> getPerInfoCtgIdList(String companyId, String categoryCd) {
		return this.queryProxy().query(SELECT_LIST_CTG_ID_QUERY, String.class).setParameter("companyId", companyId)
				.setParameter("categoryCd", categoryCd).getList();
	}

	@Override
	public void addPerInfoCtgRoot(PersonInfoCategory perInfoCtg, String contractCd) {
		this.commandProxy().insert(createPerInfoCtgCmFromDomain(perInfoCtg, contractCd));
		this.commandProxy().insert(createPerInfoCtgFromDomain(perInfoCtg));
		addOrderPerInfoCtgRoot(perInfoCtg.getPersonInfoCategoryId(), perInfoCtg.getCompanyId());
	}

	@Override
	public void addPerInfoCtgWithListCompany(PersonInfoCategory perInfoCtg, String contractCd,
			List<String> companyIdList) {
		this.commandProxy().insertAll(companyIdList.stream().map(p -> {
			return createPerInfoCtgFromDomainWithCtgId(perInfoCtg, p);
		}).collect(Collectors.toList()));
		addOrderPerInfoCtgWithListCompany(perInfoCtg.getPersonInfoCategoryId(), companyIdList);
	}

	@Override
	public void updatePerInfoCtg(PersonInfoCategory perInfoCtg, String contractCd) {
		this.commandProxy().update(createPerInfoCtgFromDomain(perInfoCtg));
		this.commandProxy().update(createPerInfoCtgCmFromDomain(perInfoCtg, contractCd));
	}

	@Override
	public void updatePerInfoCtgWithListCompany(PersonInfoCategory perInfoCtg, String contractCd,
			List<String> companyIdList) {
		this.commandProxy().updateAll(companyIdList.stream().map(p -> {
			return createPerInfoCtgFromDomainWithCtgId(perInfoCtg, p);
		}).collect(Collectors.toList()));
	}

	@Override
	public boolean checkCtgNameIsUnique(String companyId, String newCtgName) {
		List<String> categoryNames = this.queryProxy().query(SELECT_CHECK_CTG_NAME_QUERY, String.class)
				.setParameter("companyId", companyId).setParameter("categoryName", newCtgName).getList();
		if (categoryNames == null || categoryNames.isEmpty()) {
			return true;
		}
		return false;
	}

	private void addOrderPerInfoCtgRoot(String perInfoCtgId, String companyId) {
		int newdisOrderLastest = getDispOrderLastestCtgOfCompany(perInfoCtgId, companyId) + 1;
		this.commandProxy().insert(createPerInfoCtgOrderFromDomain(perInfoCtgId, companyId, newdisOrderLastest));
	}

	private void addOrderPerInfoCtgWithListCompany(String perInfoCtgId, List<String> companyIdList) {
		this.commandProxy().insertAll(companyIdList.stream().map(cid -> {
			int newdisOrderLastest = getDispOrderLastestCtgOfCompany(perInfoCtgId, cid) + 1;
			return createPerInfoCtgOrderFromDomain(perInfoCtgId, cid, newdisOrderLastest);
		}).collect(Collectors.toList()));
	}

	private int getDispOrderLastestCtgOfCompany(String perInfoCtgId, String companyId) {
		List<Integer> dispOrderLastests = this.getEntityManager()
				.createQuery(SELECT_GET_DISPORDER_CTG_OF_COMPANY_QUERY, Integer.class)
				.setParameter("perInfoCtgId", perInfoCtgId).setParameter("companyId", companyId).setMaxResults(1)
				.getResultList();
		if (dispOrderLastests != null && !dispOrderLastests.isEmpty()) {
			return dispOrderLastests.get(0);
		}
		return 0;
	}

	// mapping
	private PersonInfoCategory createDomainFromEntity(Object[] c) {
		String personInfoCategoryId = String.valueOf(c[0]);
		String categoryCode = String.valueOf(c[1]);
		String categoryName = String.valueOf(c[2]);
		int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
		String categoryParentCd = (c[4] != null) ? String.valueOf(c[4]) : null;
		int categoryType = Integer.parseInt(String.valueOf(c[5]));
		int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
		int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
		return PersonInfoCategory.createFromEntity(personInfoCategoryId, null, categoryCode, categoryParentCd,
				categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr);

	}

	private PpemtPerInfoCtg createPerInfoCtgFromDomain(PersonInfoCategory perInfoCtg) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(perInfoCtg.getPersonInfoCategoryId());
		return new PpemtPerInfoCtg(perInfoCtgPK, perInfoCtg.getCompanyId(), perInfoCtg.getCategoryCode().v(),
				perInfoCtg.getCategoryName().v(), perInfoCtg.getIsAbolition().value);

	}

	private PpemtPerInfoCtg createPerInfoCtgFromDomainWithCtgId(PersonInfoCategory perInfoCtg, String companyId) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(perInfoCtg.getPersonInfoCategoryId());
		return new PpemtPerInfoCtg(perInfoCtgPK, companyId, perInfoCtg.getCategoryCode().v(),
				perInfoCtg.getCategoryName().v(), perInfoCtg.getIsAbolition().value);

	}

	private PpemtPerInfoCtgCm createPerInfoCtgCmFromDomain(PersonInfoCategory perInfoCtg, String contractCd) {
		PpemtPerInfoCtgCmPK perInfoCtgCmPK = new PpemtPerInfoCtgCmPK(contractCd, perInfoCtg.getCategoryCode().v());
		return new PpemtPerInfoCtgCm(perInfoCtgCmPK, perInfoCtg.getCategoryParentCode().v(),
				perInfoCtg.getCategoryType().value, perInfoCtg.getPersonEmployeeType().value,
				perInfoCtg.getIsFixed().value);
	}

	private PpemtPerInfoCtgOrder createPerInfoCtgOrderFromDomain(String perInfoCtgId, String companyId, int disOrder) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(perInfoCtgId);
		return new PpemtPerInfoCtgOrder(perInfoCtgPK, companyId, disOrder);
	}
}
