package nts.uk.ctx.pereg.infra.repository.person.info.ctg;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.category.dto.DateRangeDto;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtDateRangeItem;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgCm;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgCmPK;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPerInfoCategoryRepositoty extends JpaRepository implements PerInfoCategoryRepositoty {

	private final static String SPECIAL_CTG_CODE = "CO";
	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY = String.join(" ","SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,",
			 "ca.categoryCd, ca.categoryName, ca.abolitionAtr,",
			 "co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder, co.addItemObjCls, co.initValMasterObjCls, co.canAbolition, co.salaryUseAtr, co.personnelUseAtr, co.employmentUseAtr",
			 "FROM PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co",
			 "ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd",
			 "INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId",
			 "WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid",
			 "AND ((co.salaryUseAtr = 1 AND :salaryUseAtr = 1) OR (co.personnelUseAtr = 1 AND :personnelUseAtr = 1) OR (co.employmentUseAtr = 1 AND :employmentUseAtr = 1))",
			 "OR (:salaryUseAtr =  0 AND :personnelUseAtr = 0 AND :employmentUseAtr = 0)",
			 "ORDER BY po.disporder");
	
	private final static String SELECT_DATE_RANGE_CODE = String.join(" ", "SELECT DISTINCT ctg.categoryCd, ii.itemCd FROM PpemtPerInfoCtg ctg",
			"INNER JOIN PpemtPerInfoItem ii ON ii.perInfoCtgId = ctg.ppemtPerInfoCtgPK.perInfoCtgId",
			"INNER JOIN PpemtDateRangeItem dri ON dri.ppemtPerInfoCtgPK.perInfoCtgId = ctg.ppemtPerInfoCtgPK.perInfoCtgId",
			"WHERE ii.ppemtPerInfoItemPK.perInfoItemDefId = dri.startDateItemId OR ii.ppemtPerInfoItemPK.perInfoItemDefId = dri.endDateItemId",
			"AND ctg.cid = :cid ORDER BY ctg.categoryCd");

	private final static String GET_ALL_CATEGORY_FOR_CPS007_CPS008 = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,"
			+ " ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder,"
			+ " co.addItemObjCls, co.initValMasterObjCls, co.canAbolition, co.salaryUseAtr, co.personnelUseAtr, co.employmentUseAtr"
			+ " FROM PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co"
			+ " ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND"
			+ " ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd " 
			+ " AND ca.cid = :cid AND ca.abolitionAtr = 0 "
			+ " AND ((co.salaryUseAtr = 1 AND :forPayroll = 1) OR (co.personnelUseAtr = 1 AND :forPersonnel = 1) OR (co.employmentUseAtr = 1 AND :forAttendance = 1))"
			+ " OR (:forPayroll =  0 AND :forPersonnel = 0 AND :forAttendance = 0)"
			+ " ORDER BY po.disporder";

	private final static String SELECT_CATEGORY_NO_MUL_DUP_BY_COMPANY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,"
			+ " ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder"
			+ " FROM PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co"
			+ " ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND"
			+ " ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid"
			+ " AND ca.abolitionAtr = 0 "
			+ " AND co.personEmployeeType = 2" 
			+ " AND co.categoryType != 2 " 
			+ " AND co.categoryType !=5"
			+ " AND co.initValMasterObjCls = 1 " 
			+ " AND ((co.salaryUseAtr = 1 AND :forPayroll = 1) OR (co.personnelUseAtr = 1 AND :forPersonnel = 1) OR (co.employmentUseAtr = 1 AND :forAttendance = 1))"
			+ " OR (:forPayroll =  0 AND :forPersonnel = 0 AND :forAttendance = 0)"
			+ " ORDER BY po.disporder";

	private final static String SELECT_CATEGORY_BY_CATEGORY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, co.addItemObjCls , co.initValMasterObjCls "
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd"
			+ " AND ca.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId";

	private final static String SELECT_CATEGORY_BY_PARENT_CD = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd" + " AND CO.categoryParentCd = :parentCd";

	private final static String SELECT_CATEGORY_BY_PARENT_CD_WITH_ORDER = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND"
			+ " ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd" + " AND ca.cid = :companyId"
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd"
			+ " AND CO.categoryParentCd = :parentCd ORDER BY po.disporder";

	private final static String SELECT_GET_CATEGORY_CODE_LASTEST_QUERY = "SELECT co.ppemtPerInfoCtgCmPK.categoryCd FROM PpemtPerInfoCtgCm co"
			+ " WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd ORDER BY co.ppemtPerInfoCtgCmPK.categoryCd DESC";

	private final static String SELECT_GET_DISPORDER_CTG_OF_COMPANY_QUERY = "SELECT od.disporder FROM PpemtPerInfoCtgOrder od"
			+ " WHERE od.cid = :companyId ORDER BY od.disporder DESC";

	private final static String SELECT_LIST_CTG_ID_QUERY = "SELECT c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " FROM PpemtPerInfoCtg c WHERE c.cid IN :companyIdList AND c.categoryCd = :categoryCd";

	private final static String SELECT_CHECK_CTG_NAME_QUERY = "SELECT c.categoryName"
			+ " FROM PpemtPerInfoCtg c WHERE c.cid = :companyId AND c.categoryName = :categoryName"
			+ " AND c.ppemtPerInfoCtgPK.perInfoCtgId != :ctgId";

	private final static String SELECT_CATEGORY_BY_NAME = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,"
			+ " ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder"
			+ " FROM PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co"
			+ " ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND"
			+ " ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid"
			+ " AND ca.categoryName LIKE CONCAT('%', :categoryName, '%') AND co.categoryParentCd IS NULL AND co.categoryType != 2 AND co.categoryType !=5  NULL ORDER BY po.disporder";

	private final static String GET_DATE_RANGE_ID_BY_CTG_ID = "SELECT d FROM PpemtDateRangeItem d"
			+ " WHERE d.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId";

	private final static String GET_DATE_RANGE_ID_BY_CTG_ID_2 = "SELECT d FROM PpemtDateRangeItem d"
			+ " WHERE d.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId";

	private final static String SELECT_CATEGORY_BY_CATEGORY_CD_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd" + " AND ca.categoryCd = :categoryCd"
			+ " AND ca.cid = :cid";

	private final static String SELECT_ALL_CATEGORY_BY_CATEGORY_CD_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr"
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd" + " AND ca.categoryCd = :categoryCd";

	private final static String SELECT_NO_WHERE = String.join(" ",
			"SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,",
			"co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder",
			"FROM PpemtPerInfoCtg ca",
			"INNER JOIN PpemtPerInfoCtgCm co ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd",
			"INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId");

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY_1 = SELECT_NO_WHERE
			+ " WHERE ca.cid = :cid AND co.categoryParentCd IS NULL ORDER BY po.disporder";

	private final static String SELECT_CTG_WITH_AUTH = String.join(" ",
			SELECT_NO_WHERE,
			"INNER JOIN PpemtPersonCategoryAuth au ON ca.ppemtPerInfoCtgPK.perInfoCtgId = au.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId",
			"WHERE ca.cid = :cid",
			"AND co.categoryParentCd IS NULL", 
			"AND (au.allowPersonRef = :selfAuth or 0 = :selfAuth)",
			"AND ca.abolitionAtr = 0 AND au.ppemtPersonCategoryAuthPk.roleId = :roleId",
			"AND 0 != (SELECT COUNT(i.perInfoCtgId) FROM PpemtPerInfoItem i",
			"JOIN PpemtPersonItemAuth iau ON i.ppemtPerInfoItemPK.perInfoItemDefId = iau.ppemtPersonItemAuthPk.personItemDefId",
			"AND i.perInfoCtgId = iau.ppemtPersonItemAuthPk.personInfoCategoryAuthId WHERE i.abolitionAtr = 0 AND i.perInfoCtgId = ca.ppemtPerInfoCtgPK.perInfoCtgId",
			"AND iau.ppemtPersonItemAuthPk.roleId = :roleId AND (0 = :otherAuth or (1 = :otherAuth and iau.otherPersonAuthType != 1)) ",
			"AND (0 = :selfAuth OR (1 = :selfAuth and iau.selfAuthType != 1)))",
			"AND 0 != (SELECT COUNT(c.ppemtPersonItemAuthPk.roleId) FROM PpemtPersonItemAuth c WHERE c.ppemtPersonItemAuthPk.roleId = :roleId",
			"AND c.ppemtPersonItemAuthPk.personInfoCategoryAuthId = ca.ppemtPerInfoCtgPK.perInfoCtgId AND (0 = :otherAuth or (1 = :otherAuth and c.otherPersonAuthType != 1))",
			"AND (0 = :selfAuth OR (1 = :selfAuth AND c.selfAuthType != 1)))");

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_USED = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,"
			+ " ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder"
			+ " FROM PpemtPerInfoCtg ca "
			+ " INNER JOIN PpemtPerInfoCtgCm co ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE ca.cid = :cid AND ca.abolitionAtr = 0 AND co.categoryParentCd IS NULL ORDER BY po.disporder";

	private final static String SELECT_CAT_ID_BY_CODE = String.join(" ", "SELECT c.ppemtPerInfoCtgPK.perInfoCtgId",
			"FROM PpemtPerInfoCtg c", "WHERE c.categoryCd = :categoryCd AND c.cid = :cId");

	private final static String SELECT_CTG_BY_CTGCD = String.join(" ",
			"SELECT c.ppemtPerInfoCtgPK.perInfoCtgId, c.categoryCd, c.categoryName, c.abolitionAtr",
			"FROM PpemtPerInfoCtg c WHERE c.categoryCd in :lstCtgCd AND c.cid = :cid");

	private final static String SELECT_CTG_ID_BY_CTGCD = String.join(" ", "SELECT c.ppemtPerInfoCtgPK.perInfoCtgId",
			"FROM PpemtPerInfoCtg c WHERE c.categoryCd in :lstCtgCd AND c.cid = :cid");

	@Override
	public List<PersonInfoCategory> getAllPerInfoCategory(String companyId, String contractCd, int salaryUseAtr,
			int personnelUseAtr, int employmentUseAtr) {

		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("cid", companyId)
				.setParameter("salaryUseAtr", salaryUseAtr)
				.setParameter("personnelUseAtr", personnelUseAtr)
				.setParameter("employmentUseAtr", employmentUseAtr)
				.getList(c -> {
					return createDomainVer3FromEntity(c);
				});
	}

	@Override
	public List<PersonInfoCategory> getAllCategoryForCPS007(String companyId, String contractCd, int forAttendance,
			int forPayroll, int forPersonnel) {
		return this.queryProxy().query(GET_ALL_CATEGORY_FOR_CPS007_CPS008, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("cid", companyId)
				.setParameter("forAttendance", forAttendance)
				.setParameter("forPayroll", forPayroll)
				.setParameter("forPersonnel", forPersonnel).getList(c -> {
					return createDomainVer3FromEntity(c);
				});
	}

	@Override
	public List<PersonInfoCategory> getAllPerInfoCategoryNoMulAndDupHist(String companyId, String contractCd,
			int forAttendance, int forPayroll, int forPersonnel) {
		return this.queryProxy().query(SELECT_CATEGORY_NO_MUL_DUP_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("cid", companyId)
				.setParameter("forAttendance", forAttendance).setParameter("forPayroll", forPayroll)
				.setParameter("forPersonnel", forPersonnel).getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId, String contractCd) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("perInfoCtgId", perInfoCategoryId).getSingle(c -> {
					return createDomainVer2FromEntity(c);
				});
	}

	@Override
	public String getPerInfoCtgCodeLastest(String contractCd) {
		List<String> ctgCodeLastest = this.getEntityManager()
				.createQuery(SELECT_GET_CATEGORY_CODE_LASTEST_QUERY, String.class)
				.setParameter("contractCd", contractCd).getResultList();
		return ctgCodeLastest.stream().filter(c -> c.contains(SPECIAL_CTG_CODE)).findFirst().orElse(null);
	}

	@Override
	public List<String> getPerInfoCtgIdList(List<String> companyIdList, String categoryCd) {
		List<String> results = new ArrayList<>();
		CollectionUtil.split(companyIdList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			results.addAll(this.queryProxy().query(SELECT_LIST_CTG_ID_QUERY, String.class)
				.setParameter("companyIdList", subList).setParameter("categoryCd", categoryCd).getList());
		});
		return results;
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
		List<PpemtPerInfoCtg> lstPpemtPerInfoCtg = companyIdList.stream().map(p -> {
			return createPerInfoCtgFromDomainWithCid(perInfoCtg, p);
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(lstPpemtPerInfoCtg);
		addOrderPerInfoCtgWithListCompany(lstPpemtPerInfoCtg);
	}

	@Override
	public void updatePerInfoCtg(PersonInfoCategory perInfoCtg, String contractCd) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(perInfoCtg.getPersonInfoCategoryId());
		PpemtPerInfoCtg perInfoCtgOld = this.queryProxy().find(perInfoCtgPK, PpemtPerInfoCtg.class).orElse(null);
		perInfoCtgOld.categoryName = perInfoCtg.getCategoryName().v();
		this.commandProxy().update(perInfoCtgOld);
		PpemtPerInfoCtgCmPK perInfoCtgCmPK = new PpemtPerInfoCtgCmPK(contractCd, perInfoCtgOld.categoryCd);
		PpemtPerInfoCtgCm perInfoCtgCmOld = this.queryProxy().find(perInfoCtgCmPK, PpemtPerInfoCtgCm.class)
				.orElse(null);
		perInfoCtgCmOld.categoryType = perInfoCtg.getCategoryType().value;
		this.commandProxy().update(perInfoCtgCmOld);
	}

	@Override
	public boolean checkCtgNameIsUnique(String companyId, String newCtgName, String ctgId) {
		List<String> categoryNames = this.queryProxy().query(SELECT_CHECK_CTG_NAME_QUERY, String.class)
				.setParameter("companyId", companyId).setParameter("categoryName", newCtgName)
				.setParameter("ctgId", ctgId).getList();
		if (categoryNames == null || categoryNames.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void addDateRangeItemRoot(DateRangeItem dateRangeItem) {
		this.commandProxy().insert(createDateRangeItemFromDomain(dateRangeItem));
	}

	@Override
	public void addListDateRangeItem(List<DateRangeItem> dateRangeItems) {
		this.commandProxy().insertAll(dateRangeItems.stream().map(c -> {
			return createDateRangeItemFromDomain(c);
		}).collect(Collectors.toList()));

	}

	private void addOrderPerInfoCtgRoot(String perInfoCtgId, String companyId) {
		int newdisOrderLastest = getDispOrderLastestCtgOfCompany(companyId) + 1;
		this.commandProxy().insert(createPerInfoCtgOrderFromDomain(perInfoCtgId, companyId, newdisOrderLastest));
	}

	private void addOrderPerInfoCtgWithListCompany(List<PpemtPerInfoCtg> lstPpemtPerInfoCtg) {
		this.commandProxy().insertAll(lstPpemtPerInfoCtg.stream().map(p -> {
			int newdisOrderLastest = getDispOrderLastestCtgOfCompany(p.cid) + 1;
			return createPerInfoCtgOrderFromDomain(p.ppemtPerInfoCtgPK.perInfoCtgId, p.cid, newdisOrderLastest);
		}).collect(Collectors.toList()));
	}

	private int getDispOrderLastestCtgOfCompany(String companyId) {
		List<Integer> dispOrderLastests = this.getEntityManager()
				.createQuery(SELECT_GET_DISPORDER_CTG_OF_COMPANY_QUERY, Integer.class)
				.setParameter("companyId", companyId).setMaxResults(1).getResultList();
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

	// đối ứng cho việc thêm 2 trường mới initObj, addObj
	private PersonInfoCategory createDomainVer2FromEntity(Object[] c) {
		String personInfoCategoryId = String.valueOf(c[0]);
		String categoryCode = String.valueOf(c[1]);
		String categoryName = String.valueOf(c[2]);
		int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
		String categoryParentCd = (c[4] != null) ? String.valueOf(c[4]) : null;
		int categoryType = Integer.parseInt(String.valueOf(c[5]));
		int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
		int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
		int addObj = (c[8] != null) ? Integer.parseInt(String.valueOf(c[8])) : 1;
		int initObj = (c[9] != null) ? Integer.parseInt(String.valueOf(c[9])) : 1;

		return PersonInfoCategory.createFromEntity(personInfoCategoryId, null, categoryCode, categoryParentCd,
				categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr, addObj, initObj);

	}

	// đối ứng cho việc thêm 4 trường mới canAbolition, salaryUseAtr,
	// personnelUseAtr, employmentUseAtr
	private PersonInfoCategory createDomainVer3FromEntity(Object[] c) {
		String personInfoCategoryId = String.valueOf(c[0]);
		String categoryCode = String.valueOf(c[1]);
		String categoryName = String.valueOf(c[2]);
		int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
		String categoryParentCd = (c[4] != null) ? String.valueOf(c[4]) : null;
		int categoryType = Integer.parseInt(String.valueOf(c[5]));
		int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
		int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
		int addObj = (c[9] != null) ? Integer.parseInt(String.valueOf(c[9])) : 1;
		int initObj = (c[10] != null) ? Integer.parseInt(String.valueOf(c[10])) : 1;
		int canAbolition = Integer.parseInt(String.valueOf(c[11]));
		int salaryUseAtr = Integer.parseInt(String.valueOf(c[12]));
		int personnelUseAtr = Integer.parseInt(String.valueOf(c[13]));
		int employmentUseAtr = Integer.parseInt(String.valueOf(c[14]));
		return PersonInfoCategory.createFromEntity(personInfoCategoryId, null, categoryCode, categoryParentCd,
				categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr, addObj, initObj, salaryUseAtr,
				employmentUseAtr, personnelUseAtr, canAbolition);

	}

	private PersonInfoCategory createDomainWithAbolition(Object[] c) {
		return PersonInfoCategory.createDomainWithAbolition(c[0].toString(), c[1].toString(), c[2].toString(),
				Integer.parseInt(c[3].toString()));
	}

	private PpemtPerInfoCtg createPerInfoCtgFromDomain(PersonInfoCategory perInfoCtg) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(perInfoCtg.getPersonInfoCategoryId());
		return new PpemtPerInfoCtg(perInfoCtgPK, perInfoCtg.getCompanyId(), perInfoCtg.getCategoryCode().v(),
				perInfoCtg.getCategoryName().v(), perInfoCtg.getIsAbolition().value);

	}

	private PpemtPerInfoCtg createPerInfoCtgFromDomainWithCid(PersonInfoCategory perInfoCtg, String companyId) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(IdentifierUtil.randomUniqueId());
		return new PpemtPerInfoCtg(perInfoCtgPK, companyId, perInfoCtg.getCategoryCode().v(),
				perInfoCtg.getCategoryName().v(), perInfoCtg.getIsAbolition().value);

	}

	private PpemtPerInfoCtgCm createPerInfoCtgCmFromDomain(PersonInfoCategory perInfoCtg, String contractCd) {
		PpemtPerInfoCtgCmPK perInfoCtgCmPK = new PpemtPerInfoCtgCmPK(contractCd, perInfoCtg.getCategoryCode().v());
		String categoryParentCode = (perInfoCtg.getCategoryParentCode() == null
				|| perInfoCtg.getCategoryParentCode().v().isEmpty()) ? null : perInfoCtg.getCategoryParentCode().v();
		return new PpemtPerInfoCtgCm(perInfoCtgCmPK, categoryParentCode, perInfoCtg.getCategoryType().value,
				perInfoCtg.getPersonEmployeeType().value, perInfoCtg.getIsFixed().value,
				perInfoCtg.getAddItemCls() == null ? 1 : perInfoCtg.getAddItemCls().value,
				perInfoCtg.getInitValMasterCls() == null ? 1 : perInfoCtg.getInitValMasterCls().value,
				perInfoCtg.getSalaryUseAtr().value, perInfoCtg.getPersonnelUseAtr().value,
				perInfoCtg.getEmploymentUseAtr().value, perInfoCtg.isCanAbolition() == true ? 1 : 0);
	}

	private PpemtPerInfoCtgOrder createPerInfoCtgOrderFromDomain(String perInfoCtgId, String companyId, int disOrder) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(perInfoCtgId);
		return new PpemtPerInfoCtgOrder(perInfoCtgPK, companyId, disOrder);
	}

	private PpemtDateRangeItem createDateRangeItemFromDomain(DateRangeItem dateRangeItem) {
		PpemtPerInfoCtgPK perInfoCtgPK = new PpemtPerInfoCtgPK(dateRangeItem.getPersonInfoCtgId());
		return new PpemtDateRangeItem(perInfoCtgPK, dateRangeItem.getStartDateItemId(),
				dateRangeItem.getEndDateItemId(), dateRangeItem.getDateRangeItemId());
	}

	// vinhpx: start

	@Override
	public List<PersonInfoCategory> getPerInfoCategoryByName(String companyId, String contractCd, String name) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_NAME, Object[].class).setParameter("contractCd", contractCd)
				.setParameter("cid", companyId).setParameter("categoryName", name).getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public List<PersonInfoCategory> getPerInfoCtgByParentCode(String parentCtgCd, String contractCd) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_PARENT_CD, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("parentCd", parentCtgCd).getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public DateRangeItem getDateRangeItemByCtgId(String perInfoCtgId) {
		Optional<PpemtDateRangeItem> itemOpt = this.queryProxy()
				.query(GET_DATE_RANGE_ID_BY_CTG_ID, PpemtDateRangeItem.class).setParameter("perInfoCtgId", perInfoCtgId)
				.getSingle();
		if (!itemOpt.isPresent()) {
			return null;
		}
		PpemtDateRangeItem item = itemOpt.get();
		return DateRangeItem.createFromJavaType(item.ppemtPerInfoCtgPK.perInfoCtgId, item.startDateItemId,
				item.endDateItemId, item.dateRangeItemId);
	}

	@Override
	public List<PersonInfoCategory> getPerInfoCtgByParentCdWithOrder(String parentCtgCd, String contractCd,
			String companyId, boolean isASC) {
		return this.queryProxy()
				.query(SELECT_CATEGORY_BY_PARENT_CD_WITH_ORDER + (isASC ? " ASC" : " DESC"), Object[].class)
				.setParameter("contractCd", contractCd).setParameter("parentCd", parentCtgCd)
				.setParameter("companyId", companyId).getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public Optional<DateRangeItem> getDateRangeItemByCategoryId(String perInfoCtgId) {
		Optional<PpemtDateRangeItem> itemOpt = this.queryProxy()
				.query(GET_DATE_RANGE_ID_BY_CTG_ID_2, PpemtDateRangeItem.class)
				.setParameter("perInfoCtgId", perInfoCtgId).getSingle();
		if (!itemOpt.isPresent()) {
			return Optional.empty();
		}
		PpemtDateRangeItem item = itemOpt.get();
		DateRangeItem s = DateRangeItem.createFromJavaType(item.ppemtPerInfoCtgPK.perInfoCtgId, item.startDateItemId,
				item.endDateItemId, item.dateRangeItemId);
		return Optional.of(s);
	}

	// vinhpx: end

	@Override
	public Optional<PersonInfoCategory> getPerInfoCategoryByCtgCD(String categoryCD, String companyID) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_CATEGORY_CD_QUERY, Object[].class)
				.setParameter("categoryCd", categoryCD).setParameter("cid", companyID).getSingle(c -> {
					return createDomainFromEntity(c);
				});
	}

	/**
	 * case : Employee Selected trùng với employee đang nhập
	 */
	@Override
	public List<PersonInfoCategory> getAllPerInfoCtg(String companyId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY_1, Object[].class)
				.setParameter("cid", companyId).getList(c -> {
					return createDomainPerInfoCtgFromEntity(c);
				});

	}

	/**
	 * case : Employee Selected khác với employee đang nhập
	 */
	@Override
	public List<PersonInfoCategory> getAllPerInfoCtgUsed(String companyId) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_USED, Object[].class)
				.setParameter("cid", companyId).getList(c -> {
					return createDomainPerInfoCtgFromEntity(c);
				});

	}

	// mapping
	private PersonInfoCategory createDomainPerInfoCtgFromEntity(Object[] c) {
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

	@Override
	public List<String> getAllCategoryByCtgCD(String categoryCD) {
		List<String> ctg = this.queryProxy().query(SELECT_ALL_CATEGORY_BY_CATEGORY_CD_QUERY, Object[].class)
				.setParameter("categoryCd", categoryCD).getList(c -> {
					return createDomainFromEntity(c);
				}).stream().map(c -> c.getPersonInfoCategoryId()).collect(Collectors.toList());
		return ctg;
	}

	@Override
	public int getDispOrder(String perInfoCtgId) {
		if (perInfoCtgId.equals("")) {
			return 0;
		}
		// TODO Auto-generated method stub
		return this.queryProxy().query(
				"SELECT po.disporder FROM PpemtPerInfoCtgOrder po WHERE po.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId",
				Integer.class).setParameter("perInfoCtgId", perInfoCtgId).getSingle(m -> m.intValue()).orElse(0);
	}

	@Override
	public List<PersonInfoCategory> getAllCtgWithAuth(String companyId, String roleId, int selfAuth, int otherAuth,
			boolean isOtherComapany, int forAttendance, int forPayroll, int forPersonnel) {
		String fullQuery = "";
		if (isOtherComapany) {
			fullQuery = SELECT_CTG_WITH_AUTH
					+ " AND ((au.allowOtherRef = :otherAuth AND au.allowOtherCompanyRef = 1) OR 0 = :otherAuth) "
					+ " AND ((co.salaryUseAtr = 1 AND :forPayroll = 1) OR (co.personnelUseAtr = 1 AND :forPersonnel = 1) OR (co.employmentUseAtr = 1 AND :forAttendance = 1))"
					+ " OR (:forPayroll =  0 AND :forPersonnel = 0 AND :forAttendance = 0)" 
					+ " ORDER BY po.disporder";
		} else {
			fullQuery = SELECT_CTG_WITH_AUTH
					+ " AND (au.allowOtherRef = :otherAuth  OR 0 = :otherAuth) "
					+ " AND ((co.salaryUseAtr = 1 AND :forPayroll = 1) OR (co.personnelUseAtr = 1 AND :forPersonnel = 1) OR (co.employmentUseAtr = 1 AND :forAttendance = 1))"
					+ " OR (:forPayroll =  0 AND :forPersonnel = 0 AND :forAttendance = 0)"
					+ " ORDER BY po.disporder";
		}
		
		return this.queryProxy().query(fullQuery, Object[].class).setParameter("cid", companyId)
				.setParameter("roleId", roleId).setParameter("selfAuth", selfAuth).setParameter("otherAuth", otherAuth)
				.setParameter("forAttendance", forAttendance)
				.setParameter("forPayroll", forPayroll)
				.setParameter("forPersonnel", forPersonnel).getList(c -> {
					return createDomainPerInfoCtgFromEntity(c);
				});
	}

	@Override
	public String getCatId(String cId, String categoryCode) {
		String abc = this.queryProxy().query(SELECT_CAT_ID_BY_CODE, String.class).setParameter("cId", cId)
				.setParameter("categoryCd", categoryCode).getSingle().orElse("");

		return abc;
	}

	@Override
	public List<PersonInfoCategory> getPerCtgByListCtgCd(List<String> ctgCd, String companyId) {
		List<PersonInfoCategory> lstEntity = new ArrayList<>();
		CollectionUtil.split(ctgCd, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			lstEntity.addAll(this.queryProxy().query(SELECT_CTG_BY_CTGCD, Object[].class)
				.setParameter("lstCtgCd", subList).setParameter("cid", companyId)
				.getList(x -> createDomainWithAbolition(x)));
		});
		return lstEntity;
	}

	@Override
	public void updateAbolition(List<PersonInfoCategory> ctg, String companyId) {
		ctg.forEach(x -> {
			Optional<PpemtPerInfoCtg> entityOpt = this.queryProxy()
					.find(new PpemtPerInfoCtgPK(x.getPersonInfoCategoryId()), PpemtPerInfoCtg.class);
			if (entityOpt.isPresent()) {
				PpemtPerInfoCtg entity = entityOpt.get();
				entity.categoryName = x.getCategoryName() == null ? null : x.getCategoryName().v();
				entity.abolitionAtr = x.getIsAbolition().value;
				this.commandProxy().update(entity);
			}
		});

	}

	@Override
	public List<String> getAllCtgId(List<String> ctgCd, String companyId) {
		List<String> ctgIdLst = new ArrayList<>();
		CollectionUtil.split(ctgCd, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			ctgIdLst.addAll(this.queryProxy().query(SELECT_CTG_ID_BY_CTGCD, String.class)
				.setParameter("lstCtgCd", subList).setParameter("cid", companyId).getList());
		});
		return ctgIdLst;
	}

	@Override
	public void updateAbolition(List<PersonInfoCategory> ctg) {
		ctg.forEach(x -> {
			Optional<PpemtPerInfoCtg> entityOpt = this.queryProxy()
					.find(new PpemtPerInfoCtgPK(x.getPersonInfoCategoryId()), PpemtPerInfoCtg.class);
			if (entityOpt.isPresent()) {
				PpemtPerInfoCtg entity = entityOpt.get();
				entity.abolitionAtr = x.getIsAbolition().value;
				this.commandProxy().update(entity);
			}
		});

	}

	@Override
	public List<DateRangeDto> dateRangeCode() {
		String cid = AppContexts.user().companyId();
		// Get startDate and endDate of category history
		List<Object[]> query = queryProxy().query(SELECT_DATE_RANGE_CODE, Object[].class).setParameter("cid", cid).getList();
		return query.stream().map(m -> m[0].toString()).distinct().map(m -> {
			List<Object[]> record = query.stream().filter(f -> f[0].toString().equals(m)).collect(Collectors.toList());

			if (record.size() == 2) {
				return new DateRangeDto(m, record.get(0)[1].toString(), record.get(1)[1].toString());
			}

			return null;
		}).filter(f -> f != null).collect(Collectors.toList());
	}
}
