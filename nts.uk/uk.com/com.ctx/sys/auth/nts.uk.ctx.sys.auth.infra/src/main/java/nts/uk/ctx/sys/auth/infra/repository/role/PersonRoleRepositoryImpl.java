package nts.uk.ctx.sys.auth.infra.repository.role;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonInfoCategoryExportDetail;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;
import nts.uk.ctx.sys.auth.infra.entity.role.SacmtPersonRole;

@Stateless
public class PersonRoleRepositoryImpl extends JpaRepository implements PersonRoleRepository {
	
	private final static String SELECT_COMMON_FIELD = String.join(" ", "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId,",
			"i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,",
			"ic.itemParentCd, ic.systemRequiredAtr, ic.requireChangabledAtr, ic.fixedAtr, ic.itemType,",
			"ic.dataType, ic.timeItemMin, ic.timeItemMax, ic.timepointItemMin, ic.timepointItemMax, ic.dateItemType,",
			"ic.stringItemType, ic.stringItemLength, ic.stringItemDataType, ic.numericItemMin, ic.numericItemMax, ic.numericItemAmountAtr,",
			"ic.numericItemMinusAtr, ic.numericItemDecimalPart, ic.numericItemIntegerPart,",
			"ic.selectionItemRefType, ic.selectionItemRefCode, i.perInfoCtgId, ic.relatedCategoryCode, ic.resourceId, ic.canAbolition, i.initValue");
	
	private final static String JOIN_COMMON_TABLE = String.join(" ",
			"FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId",
			"INNER JOIN PpemtPerInfoItemCm ic ON c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd",
			"AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd INNER JOIN PpemtPerInfoItemOrder io",
			"ON io.ppemtPerInfoItemPK.perInfoItemDefId = i.ppemtPerInfoItemPK.perInfoItemDefId AND io.perInfoCtgId = i.perInfoCtgId");
	
	private final static String SELECT_NO_WHERE = String.join(" ", SELECT_COMMON_FIELD, " ,io.disporder ", JOIN_COMMON_TABLE);
	
	private static final String SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY = String.join(" ",
			"SELECT DISTINCT c.ppemtPerInfoCtgPK.perInfoCtgId, c.categoryCd, c.categoryName, ",
			"cm.categoryType, p.allowPersonRef, p.allowOtherRef, cm.personEmployeeType,",
			"CASE WHEN p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId IS NOT NULL  THEN 'True' ELSE 'False' END AS IsConfig,",
			"(select count(ii) from PpemtPerInfoItem ii where ii.perInfoCtgId=c.ppemtPerInfoCtgPK.perInfoCtgId and  ii.abolitionAtr =0) as count_i ,",
			"(select count(ia) from PpemtPersonItemAuth ia where ia.ppemtPersonItemAuthPk.personInfoCategoryAuthId=c.ppemtPerInfoCtgPK.perInfoCtgId and ia.ppemtPersonItemAuthPk.roleId=p.ppemtPersonCategoryAuthPk.roleId) as count_ia",
			"FROM PpemtPerInfoCtg c" + " INNER JOIN PpemtPerInfoCtgCm cm",
			"ON c.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd",
			"AND cm.ppemtPerInfoCtgCmPK.contractCd = :contractCd", "INNER JOIN PpemtPerInfoCtgOrder co",
			"ON c.ppemtPerInfoCtgPK.perInfoCtgId = co.ppemtPerInfoCtgPK.perInfoCtgId",
			"INNER JOIN PpemtPerInfoItem i" + " ON  c.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId",
			"LEFT JOIN PpemtPersonCategoryAuth p ",
			"ON p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId  = c.ppemtPerInfoCtgPK.perInfoCtgId",
			"AND p.ppemtPersonCategoryAuthPk.roleId = :roleId" + " WHERE c.cid = :companyId", "AND c.abolitionAtr = 0",
			// them dieu kien luong, nhan su, viec lam
			"AND ((cm.salaryUseAtr = 1 AND :salaryUseAtr = 1) OR (cm.personnelUseAtr = 1 AND :personnelUseAtr = 1) OR (cm.employmentUseAtr = 1 AND :employmentUseAtr = 1)) OR (:salaryUseAtr =  0 AND  :personnelUseAtr = 0 AND :employmentUseAtr = 0)",
			"ORDER BY co.disporder");
	
	private final static String CONDITION_FOR_007008 = "ic.ppemtPerInfoItemCmPK.contractCd = :contractCd AND i.perInfoCtgId IN :lstPerInfoCategoryId AND i.abolitionAtr = 0 AND (ic.itemParentCd IS NULL OR ic.itemParentCd = '')  ORDER BY io.disporder";
	
	private final static String SELECT_ALL_ITEM_BY_LIST_CATEGORY_ID_QUERY = String.join(" ", SELECT_NO_WHERE, "WHERE",
			CONDITION_FOR_007008);
	
	/**
	 * JPQL: find (without where)
	 */
	private static final String FIND_NO_WHERE = "SELECT e FROM SacmtPersonRole e";

	/**
	 * JPQL: find by role id
	 */
	private static final String FIND_BY_ROLE_ID = FIND_NO_WHERE + " WHERE e.roleId = :roleId ";

	/**
	 * JPQL: find by list role id
	 */
	private static final String FIND_BY_LIST_ROLE_ID = FIND_NO_WHERE + " WHERE e.roleId IN :roleIds ";

	@Override
	public Optional<PersonRole> find(String roleId) {
		SacmtPersonRole entity = this.queryProxy().query(FIND_BY_ROLE_ID, SacmtPersonRole.class)
				.setParameter("roleId", roleId).getSingleOrNull();
		PersonRole domain = new PersonRole();
		if (entity != null) {
			domain = toDomain(entity);
		}
		return Optional.of(domain);
	}

	private static PersonRole toDomain(SacmtPersonRole entity) {
		PersonRole domain = new PersonRole();
		domain.setRoleId(entity.getRoleId());
		domain.setReferFutureDate(entity.isReferFutureDate());
		return domain;
	}

	@Override
	public List<PersonRole> find(List<String> roleIds) {
		List<PersonRole> result = new ArrayList<>();
		if(roleIds != null){
			CollectionUtil.split(roleIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				result.addAll(this.queryProxy().query(FIND_BY_LIST_ROLE_ID, SacmtPersonRole.class)
					.setParameter("roleIds", subList).getList().stream().map(x -> toDomain(x)).collect(Collectors.toList()));
			});
		}
		return result;
	}

	public void insert(PersonRole personRole) {
		this.commandProxy().insert(toEntity(personRole));
	}

	@Override
	public void update(PersonRole personRole) {
		SacmtPersonRole updateEntity = this.queryProxy().find(personRole.getRoleId(), SacmtPersonRole.class).get();
		updateEntity.setReferFutureDate(personRole.getReferFutureDate());
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void remove(String roleId) {	
			this.commandProxy().remove(SacmtPersonRole.class, roleId);
	}

	
	private static SacmtPersonRole  toEntity(PersonRole personRole){
		SacmtPersonRole entity = new SacmtPersonRole();
		entity.setRoleId(personRole.getRoleId());
		entity.setReferFutureDate(personRole.getReferFutureDate());
		return entity;
	}

	@Override
	public List<PersonInfoCategoryExportDetail> getAllCategory(String roleId, String contractCd, String companyId,
			int salaryUseAtr, int personnelUseAtr, int employmentUseAtr) {
		return this.queryProxy().query(SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY, Object[].class)
				.setParameter("roleId", roleId).setParameter("contractCd", contractCd)
				.setParameter("companyId", companyId)
				.setParameter("salaryUseAtr", salaryUseAtr)
				.setParameter("personnelUseAtr", personnelUseAtr)
				.setParameter("employmentUseAtr", employmentUseAtr)
				.getList(c -> toDomain(c));
	}
	
	private static PersonInfoCategoryExportDetail toDomain(Object[] entity) {
		val domain = new PersonInfoCategoryExportDetail();
		domain.setCategoryId(entity[0].toString());
		domain.setCategoryCode(entity[1].toString());
		domain.setCategoryName(entity[2].toString());
		domain.setCategoryType(Integer.valueOf(entity[3].toString()));
		if (entity[4] != null) {
			domain.setAllowPersonRef(Integer.valueOf(entity[4].toString()));
		}
		if (entity[5] != null) {
			domain.setAllowOtherRef(Integer.valueOf(entity[5].toString()));
		}
		domain.setPersonEmployeeType(Integer.valueOf(entity[6].toString()));
		boolean isHigher = Integer.valueOf(entity[8].toString()) > Integer.valueOf(entity[9].toString());
		domain.setSetting(!isHigher ? Boolean.valueOf(entity[7].toString()) : false);
		return domain;
	}
	
	private static Comparator<Object[]> SORT_BY_DISPORDER = (o1, o2) -> {
		return ((int) o1[32]) - ((int) o2[32]); // index 31 for [disporder] 
	};
	
	@Override
	public Map<String, List<Object[]>> getAllPerInfoItemDefByListCategoryId(List<String> lstPerInfoCategoryId,
			String contractCd) {
		List<Object[]> lstObj = new ArrayList<>();
		CollectionUtil.split(lstPerInfoCategoryId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			lstObj.addAll(this.queryProxy().query(SELECT_ALL_ITEM_BY_LIST_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("contractCd", contractCd).setParameter("lstPerInfoCategoryId", subList).getList());
		});
		lstObj.sort(SORT_BY_DISPORDER);
		
		// groupBy categoryId 
		Map<String, List<Object[]>> result = lstObj.stream().collect(Collectors.groupingBy(x -> String.valueOf(x[27])));
		
		return result;
	}
}
