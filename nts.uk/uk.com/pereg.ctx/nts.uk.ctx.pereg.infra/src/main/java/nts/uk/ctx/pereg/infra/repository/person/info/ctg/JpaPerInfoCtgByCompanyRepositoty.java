package nts.uk.ctx.pereg.infra.repository.person.info.ctg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtgSort;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtgPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPerInfoCtgByCompanyRepositoty extends JpaRepository implements PerInfoCtgByCompanyRepositoty {

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY = "SELECT ca.ppemtCtgPK.perInfoCtgId, ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, co.canAbolition "
			+ " FROM  PpemtCtg ca, PpemtCtgCommon co"
			+ " WHERE ca.categoryCd = co.ppemtCtgCommonPK.categoryCd"
			+ " AND co.ppemtCtgCommonPK.contractCd = :contractCd"
			+ " AND ca.ppemtCtgPK.perInfoCtgId = :perInfoCtgId" + " AND ca.cid =:cid";

	private final static String SELECT_REQUIRED_ITEMS_IDS = "SELECT DISTINCT i.ppemtItemPK.perInfoItemDefId FROM PpemtCtg a"
			+ " INNER JOIN PpemtItem i"
			+ " ON a.ppemtCtgPK.perInfoCtgId = i.perInfoCtgId "
			+ " INNER JOIN PpemtItemCommon c ON i.itemCd = c.ppemtItemCommonPK.itemCd"
			+ " WHERE c.ppemtItemCommonPK.contractCd = :contractCd AND c.systemRequiredAtr = 1 "
			+ " AND i.perInfoCtgId = :perInfoCtgId";

	private final static String FIND_ALL_BY_COMPANY = String.join(" ", "SELECT po FROM PpemtCtg ca INNER JOIN PpemtCtgCommon co",
			"ON ca.categoryCd = co.ppemtCtgCommonPK.categoryCd",
			"INNER JOIN PpemtCtgSort po",
			"ON ca.cid = po.cid AND ca.ppemtCtgPK.perInfoCtgId = po.ppemtCtgPK.perInfoCtgId",
			"WHERE co.ppemtCtgCommonPK.contractCd = :contractCd AND ca.cid = :cid",
			"AND ((co.salaryUseAtr = 1 AND :salaryUseAtr = 1) OR (co.personnelUseAtr = 1 AND :personnelUseAtr = 1) OR (co.employmentUseAtr = 1 AND :employmentUseAtr = 1))",
			"OR (:salaryUseAtr =  0 AND :personnelUseAtr = 0 AND :employmentUseAtr = 0)",
			"ORDER BY po.disporder");

	private final static String SELECT_CTG_NAME_BY_CTG_CD_QUERY = "SELECT c.categoryName"
			+ " FROM PpemtCtg c WHERE c.cid = :cid AND c.categoryCd = :categoryCd";
	
	private final static String SELECT_CHECK_CTG_NAME_QUERY = "SELECT c.categoryName"
			+ " FROM PpemtCtg c WHERE c.cid = :companyId AND c.categoryName = :categoryName"
			+ " AND c.ppemtCtgPK.perInfoCtgId != :ctgId";
	
	private final static String SELECT_CTG_ORDER_BY_IDS = String.join(" ", 
			"SELECT c.ppemtCtgPK.perInfoCtgId, c.disporder",
			"FROM PpemtCtgSort c",
			"WHERE c.ppemtCtgPK.perInfoCtgId IN :ctgIds",
			"AND c.cid = :cid");

	private final static String SELECT_ITEMS_ORDER_BY_IDS = String.join(" ", 
			"SELECT i.ppemtItemPK.perInfoItemDefId, i.disporder",
			"FROM PpemtItemSort i",
			"WHERE i.perInfoCtgId IN :ctgIds",
			"AND i.ppemtItemPK.perInfoItemDefId IN :itIds");

	private static PpemtCtg toEntity(PersonInfoCategory domain) {
		PpemtCtg entity = new PpemtCtg();
		entity.ppemtCtgPK = new PpemtCtgPK(domain.getPersonInfoCategoryId());
		entity.cid = AppContexts.user().companyId();
		entity.categoryCd = domain.getCategoryCode().v();
		entity.categoryName = domain.getCategoryName().v();
		entity.abolitionAtr = domain.getIsAbolition().value;
		return entity;

	}

	private static PpemtCtgSort toEntityCategoryOrder(PersonInfoCtgOrder domain) {
		PpemtCtgSort entity = new PpemtCtgSort();
		entity.ppemtCtgPK = new PpemtCtgPK(domain.getCategoryId());
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
		this.commandProxy().updateAll(domainList.stream().map(domain -> toEntityCategoryOrder(domain)).collect(Collectors.toList()));
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
		List<PpemtCtgSort> entities = this.queryProxy().query(FIND_ALL_BY_COMPANY, PpemtCtgSort.class)
				.setParameter("cid", companyId)
				.setParameter("contractCd", contractCd)
				.setParameter("salaryUseAtr", salaryUseAtr)
				.setParameter("personnelUseAtr", personnelUseAtr)
				.setParameter("employmentUseAtr", employmentUseAtr)
				.getList();
		

		return entities.stream().map(entity -> PersonInfoCtgOrder.createCategoryOrder(companyId,
				entity.ppemtCtgPK.perInfoCtgId, entity.disporder)).collect(Collectors.toList());
	}
	
	public HashMap<Integer, HashMap<String, Integer>> getOrderList(List<String> categoryIds, List<String> itemDefinitionIds) {
		HashMap<String, Integer> ctgs = new HashMap<>();
		HashMap<String, Integer> items = new HashMap<>();
		String companyId = AppContexts.user().companyId();
		
		if (categoryIds == null || categoryIds.isEmpty()) {
			categoryIds = Arrays.asList(IdentifierUtil.randomUniqueId());
		}
		
		CollectionUtil.split(categoryIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, _catIds -> {
			this.queryProxy().query(SELECT_CTG_ORDER_BY_IDS, Object[].class).setParameter("cid", companyId)
					.setParameter("ctgIds", _catIds).getList().stream().forEach(ctg -> {
						ctgs.putIfAbsent(ctg[0].toString(), ctg[1] == null ? -1 : new Integer(ctg[1].toString()));
					});

			if (!CollectionUtil.isEmpty(itemDefinitionIds)) {
				CollectionUtil.split(itemDefinitionIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, _itemIds -> {
					this.queryProxy().query(SELECT_ITEMS_ORDER_BY_IDS, Object[].class).setParameter("ctgIds", _catIds)
							.setParameter("itIds", _itemIds).getList().forEach(it -> {
								items.putIfAbsent(it[0].toString(), it[1] == null ? -1 : new Integer(it[1].toString()));
							});
				});
			} else {
				this.queryProxy().query(SELECT_ITEMS_ORDER_BY_IDS, Object[].class).setParameter("ctgIds", _catIds)
						.setParameter("itIds", Arrays.asList(IdentifierUtil.randomUniqueId())).getList().forEach(it -> {
							items.putIfAbsent(it[0].toString(), it[1] == null ? -1 : new Integer(it[1].toString()));
						});
			}
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
