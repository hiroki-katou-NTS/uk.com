package nts.uk.ctx.pereg.infra.repository.person.info.ctg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgPK;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemOrder;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPerInfoCtgByCompanyRepositoty extends JpaRepository implements PerInfoCtgByCompanyRepositoty {

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, co.canAbolition "
			+ " FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co"
			+ " WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND co.ppemtPerInfoCtgCmPK.contractCd = :contractCd"
			+ " AND ca.ppemtPerInfoCtgPK.perInfoCtgId = :perInfoCtgId" + " AND ca.cid =:cid";

	private final static String SELECT_REQUIRED_ITEMS_IDS = "SELECT DISTINCT i.ppemtPerInfoItemPK.perInfoItemDefId FROM PpemtPerInfoCtg a"
			+ " INNER JOIN PpemtPerInfoItem i"
			+ " ON a.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId "
			+ " INNER JOIN PpemtPerInfoItemCm c ON i.itemCd = c.ppemtPerInfoItemCmPK.itemCd"
			+ " WHERE c.ppemtPerInfoItemCmPK.contractCd = :contractCd AND c.systemRequiredAtr = 1 "
			+ " AND i.perInfoCtgId = :perInfoCtgId";

	private final static String FIND_ALL_BY_COMPANY = String.join(" ", "SELECT po FROM PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co",
			"ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd",
			"INNER JOIN PpemtPerInfoCtgOrder po",
			"ON ca.cid = po.cid AND ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId",
			"WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid",
			"AND ((co.salaryUseAtr = 1 AND :salaryUseAtr = 1) OR (co.personnelUseAtr = 1 AND :personnelUseAtr = 1) OR (co.employmentUseAtr = 1 AND :employmentUseAtr = 1))",
			"OR (:salaryUseAtr =  0 AND :personnelUseAtr = 0 AND :employmentUseAtr = 0)",
			"ORDER BY po.disporder");

	private final static String SELECT_CTG_NAME_BY_CTG_CD_QUERY = "SELECT c.categoryName"
			+ " FROM PpemtPerInfoCtg c WHERE c.cid = :cid AND c.categoryCd = :categoryCd";
	
	private final static String SELECT_CHECK_CTG_NAME_QUERY = "SELECT c.categoryName"
			+ " FROM PpemtPerInfoCtg c WHERE c.cid = :companyId AND c.categoryName = :categoryName"
			+ " AND c.ppemtPerInfoCtgPK.perInfoCtgId != :ctgId";
	
	private final static String SELECT_CTG_ORDER_BY_IDS = String.join(" ", 
			"SELECT c.ppemtPerInfoCtgPK.perInfoCtgId, c.disporder",
			"FROM PpemtPerInfoCtgOrder c",
			"WHERE c.ppemtPerInfoCtgPK.perInfoCtgId IN :ctgIds",
			"AND c.cid = :cid");

	private final static String SELECT_ITEMS_ORDER_BY_IDS = String.join(" ", 
			"SELECT i.ppemtPerInfoItemPK.perInfoItemDefId, i.disporder",
			"FROM PpemtPerInfoItemOrder i",
			"WHERE i.perInfoCtgId IN :ctgIds",
			"AND i.ppemtPerInfoItemPK.perInfoItemDefId IN :itIds");

	private static PpemtPerInfoCtg toEntity(PersonInfoCategory domain) {
		PpemtPerInfoCtg entity = new PpemtPerInfoCtg();
		entity.ppemtPerInfoCtgPK = new PpemtPerInfoCtgPK(domain.getPersonInfoCategoryId());
		entity.cid = AppContexts.user().companyId();
		entity.categoryCd = domain.getCategoryCode().v();
		entity.categoryName = domain.getCategoryName().v();
		entity.abolitionAtr = domain.getIsAbolition().value;
		return entity;

	}

	private static PpemtPerInfoCtgOrder toEntityCategoryOrder(PersonInfoCtgOrder domain) {
		PpemtPerInfoCtgOrder entity = new PpemtPerInfoCtgOrder();
		entity.ppemtPerInfoCtgPK = new PpemtPerInfoCtgPK(domain.getCategoryId());
		entity.cid = domain.getCompanyId();
		entity.disporder = domain.getDisorder();
		return entity;
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
		int canAbolition = Integer.parseInt(String.valueOf(c[8]));
		return PersonInfoCategory.createFromEntity(personInfoCategoryId, AppContexts.user().companyId(), categoryCode,
				categoryParentCd, categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr, canAbolition);
	}
	
	@Override
	public void update(PersonInfoCategory domain) {
		try {
			this.commandProxy().update(toEntity(domain));
		} catch (Exception e) {
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCtgRepositoty#
	 * getDetailCategoryInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<PersonInfoCategory> getDetailCategoryInfo(String companyId, String categoryId, String contractCd) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("cid", companyId)
				.setParameter("contractCd", contractCd)
				.setParameter("perInfoCtgId", categoryId).getSingle(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public List<String> getItemInfoId(String categoryId, String contractCd) {
		return queryProxy().query(SELECT_REQUIRED_ITEMS_IDS, String.class)
				.setParameter("contractCd", contractCd)
				.setParameter("perInfoCtgId", categoryId)
				.getList();
	}

	@Override
	public void addPerCtgOrder(PersonInfoCtgOrder domain) {
		this.commandProxy().update(toEntityCategoryOrder(domain));

	}
	
	@Override
	public void updatePerCtgOrder(List<PersonInfoCtgOrder> domainList) {
		List<PpemtPerInfoCtgOrder> orderEntities = domainList.stream().map(domain -> toEntityCategoryOrder(domain))
				.collect(Collectors.toList());
		this.commandProxy().updateAll(orderEntities);
	}

	@Override
	public String getNameCategoryInfo(String companyId, String categoryCd) {
		return this.queryProxy().query(SELECT_CTG_NAME_BY_CTG_CD_QUERY, String.class)
				.setParameter("cid", companyId)
				.setParameter("categoryCd", categoryCd)
				.getSingle().orElse("null");
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
	public List<PersonInfoCtgOrder> getOrderList(String companyId,String contractCd, int salaryUseAtr,
			int personnelUseAtr, int employmentUseAtr) {
		List<PpemtPerInfoCtgOrder> entities = this.queryProxy().query(FIND_ALL_BY_COMPANY, PpemtPerInfoCtgOrder.class)
				.setParameter("cid", companyId)
				.setParameter("contractCd", contractCd)
				.setParameter("salaryUseAtr", salaryUseAtr)
				.setParameter("personnelUseAtr", personnelUseAtr)
				.setParameter("employmentUseAtr", employmentUseAtr)
				.getList();
		

		return entities.stream().map(entity -> PersonInfoCtgOrder.createCategoryOrder(companyId,
				entity.ppemtPerInfoCtgPK.perInfoCtgId, entity.disporder)).collect(Collectors.toList());
	}
	
	public HashMap<Integer, HashMap<String, Integer>> getOrderList(List<String> categoryIds, List<String> itemDefinitionIds) {
		HashMap<String, Integer> ctgs = new HashMap<>();
		HashMap<String, Integer> items = new HashMap<>();
		String companyId = AppContexts.user().companyId();
		
		if (categoryIds == null || categoryIds.isEmpty()) {
			categoryIds = Arrays.asList(IdentifierUtil.randomUniqueId());
		}

		if (itemDefinitionIds == null || itemDefinitionIds.isEmpty()) {
			itemDefinitionIds = Arrays.asList(IdentifierUtil.randomUniqueId());
		}

		this.queryProxy().query(SELECT_CTG_ORDER_BY_IDS, Object[].class).setParameter("cid", companyId)
				.setParameter("ctgIds", categoryIds).getList().stream().forEach(ctg -> {
					ctgs.putIfAbsent(ctg[0].toString(), ctg[1] == null ? -1 : new Integer(ctg[1].toString()));
				});

		this.queryProxy().query(SELECT_ITEMS_ORDER_BY_IDS, Object[].class).setParameter("ctgIds", categoryIds)
				.setParameter("itIds", itemDefinitionIds).getList().forEach(it -> {
					items.putIfAbsent(it[0].toString(), it[1] == null ? -1 : new Integer(it[1].toString()));
				});

		return new HashMap<Integer, HashMap<String, Integer>>() {
			private static final long serialVersionUID = 1L;
			{
				put(0, ctgs);
				put(1, items);
			}
		};
	}
}
