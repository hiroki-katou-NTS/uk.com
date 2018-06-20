package nts.uk.ctx.pereg.infra.repository.roles.auth.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryDetail;
import nts.uk.ctx.pereg.infra.entity.roles.auth.category.PpemtPersonCategoryAuth;
import nts.uk.ctx.pereg.infra.entity.roles.auth.category.PpemtPersonCategoryAuthPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaPersonInfoCategoryAuthRepository extends JpaRepository implements PersonInfoCategoryAuthRepository {


	private final String SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY = "SELECT DISTINCT c.ppemtPerInfoCtgPK.perInfoCtgId, c.categoryCd, c.categoryName, "
			+ " cm.categoryType, p.allowPersonRef, p.allowOtherRef, cm.personEmployeeType,"
			+ " CASE WHEN p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId IS NOT NULL  THEN 'True' ELSE 'False' END AS IsConfig,"
			+ "(select count(ii) from PpemtPerInfoItem ii where ii.perInfoCtgId=c.ppemtPerInfoCtgPK.perInfoCtgId and  ii.abolitionAtr =0) as count_i ,"
			+ "(select count(ia) from PpemtPersonItemAuth ia where ia.ppemtPersonItemAuthPk.personInfoCategoryAuthId=c.ppemtPerInfoCtgPK.perInfoCtgId and ia.ppemtPersonItemAuthPk.roleId=p.ppemtPersonCategoryAuthPk.roleId) as count_ia"
			+ " FROM PpemtPerInfoCtg c" + " INNER JOIN PpemtPerInfoCtgCm cm"
			+ " ON c.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd"
			+ " AND cm.ppemtPerInfoCtgCmPK.contractCd = :contractCd" + " INNER JOIN PpemtPerInfoCtgOrder co"
			+ "	ON c.ppemtPerInfoCtgPK.perInfoCtgId = co.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItem i" + " ON  c.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId"
			+ " LEFT JOIN PpemtPersonCategoryAuth p "
			+ " ON p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId  = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " AND p.ppemtPersonCategoryAuthPk.roleId = :roleId" + " WHERE c.cid = :companyId"
			+ " AND c.abolitionAtr = 0" + "	ORDER BY co.disporder";

	private static final String SELECT_CATEGORY_BY_CATEGORY_LIST_ID_QUERY = "SELECT DISTINCT c.ppemtPerInfoCtgPK.perInfoCtgId, c.categoryCd, c.categoryName, cm.categoryType "
			+ " FROM PpemtPerInfoCtg c" + " INNER JOIN PpemtPerInfoCtgCm cm"
			+ " ON c.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd" + " INNER JOIN PpemtPerInfoCtgOrder co"
			+ "	ON c.ppemtPerInfoCtgPK.perInfoCtgId = co.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItem i" + " ON  c.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId"
			+ " INNER JOIN PpemtPersonCategoryAuth p "
			+ " ON p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId  = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN SacmtPersonRole pr" + " ON pr.roleId = p.ppemtPersonCategoryAuthPk.roleId"
			+ " WHERE c.cid = :companyId" + " AND c.abolitionAtr = 0" + " AND p.allowOtherRef = 1"
			+ "	AND c.ppemtPerInfoCtgPK.perInfoCtgId IN :perInfoCtgIdlst" + "	ORDER BY co.disporder ";

	private static final String SEL_CATEGORY_BY_ROLEID = "SELECT c FROM PpemtPersonCategoryAuth c  WHERE c.ppemtPersonCategoryAuthPk.roleId =:roleId ";

	private static final String SEL_CATEGORY_BY_ABOLITION_ATR = "SELECT  c.perInfoCtgId, d.categoryCd, d.categoryName, d.abolitionAtr, c.abolitionAtr, c.requiredAtr, cm.personEmployeeType , "
			+ "CASE WHEN c.perInfoCtgId IS NULL THEN 'False' ELSE 'True' END AS IsConfig" + " FROM PpemtPerInfoCtg d "
			+ " INNER JOIN   PpemtPerInfoItem c " + " ON  d.ppemtPerInfoCtgPK.perInfoCtgId = c.perInfoCtgId"
			+ " WHERE d.cid = :CID  AND d.abolitionAtr = 0 AND c.abolitionAtr = 0";

	private static final String SEL_ALL_CATEGORY = "SELECT c.ppemtPerInfoCtgPK.perInfoCtgId, c.categoryCd, c.categoryName, "
			+ " cm.categoryType, p.allowPersonRef, p.allowOtherRef, cm.personEmployeeType ,"
			+ "CASE WHEN p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId IS NULL THEN 'False' ELSE 'True' END AS IsConfig"
			+ " FROM PpemtPerInfoCtg c LEFT JOIN PpemtPersonCategoryAuth p "
			+ " ON p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId  = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " AND p.ppemtPersonCategoryAuthPk.roleId = :roleId" + " LEFT JOIN PpemtPerInfoCtgCm cm"
			+ " ON c.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd " + " WHERE c.cid = :CID";

	private static final String SEE_BY_ROLEID_AND_CTG_ID_LIST = "SELECT ctgAuth FROM PpemtPersonCategoryAuth ctgAuth"
			+ " WHERE ctgAuth.ppemtPersonCategoryAuthPk.roleId = :roleId"
			+ " AND ctgAuth.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId IN :categoryIdList";

	private static final String DEL_BY_ROLE_ID = " DELETE  FROM PpemtPersonCategoryAuth c"
			+ " WHERE c.ppemtPersonCategoryAuthPk.roleId =:roleId";

	private static PersonInfoCategoryAuth toDomain(PpemtPersonCategoryAuth entity) {
		val domain = PersonInfoCategoryAuth.createFromJavaType(entity.ppemtPersonCategoryAuthPk.roleId,
				entity.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId, entity.allowPersonRef, entity.allowOtherRef,
				entity.allowOtherCompanyRef, entity.selfPastHisAuth, entity.selfFutureHisAuth, entity.selfAllowAddHis,
				entity.selfAllowDelHis, entity.otherPastHisAuth, entity.otherFutureHisAuth, entity.otherAllowAddHis,
				entity.otherAllowDelHis, entity.selfAllowAddMulti, entity.selfAllowDelMulti, entity.otherAllowAddMulti,
				entity.otherAllowDelMulti);
		return domain;
	}


	private static PersonInfoCategoryDetail toDomain(Object[] entity) {
		val domain = new PersonInfoCategoryDetail();
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

	private static PersonInfoCategoryDetail toDomainLess(Object[] entity) {
		val domain = new PersonInfoCategoryDetail();
		domain.setCategoryId(entity[0].toString());
		domain.setCategoryCode(entity[1].toString());
		domain.setCategoryName(entity[2].toString());
		domain.setCategoryType((int) entity[3]);
		return domain;
	}

	private static PpemtPersonCategoryAuth toEntity(PersonInfoCategoryAuth domain) {
		PpemtPersonCategoryAuth entity = new PpemtPersonCategoryAuth();
		entity.ppemtPersonCategoryAuthPk = new PpemtPersonCategoryAuthPk(domain.getRoleId(),
				domain.getPersonInfoCategoryAuthId());
		entity.allowOtherRef = domain.getAllowOtherRef().value;
		entity.allowPersonRef = domain.getAllowPersonRef().value;
		entity.otherAllowAddHis = domain.getOtherAllowAddHis() == null? null: domain.getOtherAllowAddHis().value;
		entity.otherAllowDelHis = domain.getOtherAllowDelHis() == null? null:   domain.getOtherAllowDelHis().value;
		entity.selfPastHisAuth = domain.getSelfPastHisAuth() == null? null: domain.getSelfPastHisAuth().value;
		entity.selfFutureHisAuth = domain.getSelfFutureHisAuth() == null? null:domain.getSelfFutureHisAuth().value;
		entity.selfAllowAddHis = domain.getSelfAllowAddHis() == null ? null: domain.getSelfAllowAddHis().value;
		entity.selfAllowDelHis = domain.getSelfAllowDelHis() == null? null: domain.getSelfAllowDelHis().value;
		entity.otherPastHisAuth = domain.getOtherPastHisAuth() == null? null: domain.getOtherPastHisAuth().value;
		entity.otherFutureHisAuth = domain.getOtherFutureHisAuth() == null? null: domain.getOtherFutureHisAuth().value;
		entity.selfAllowAddMulti = domain.getSelfAllowAddMulti() == null? null: domain.getSelfAllowAddMulti().value;
		entity.selfAllowDelMulti = domain.getSelfAllowDelMulti() == null? null: domain.getSelfAllowDelMulti().value;
		entity.otherAllowAddMulti = domain.getOtherAllowAddMulti() == null? null: domain.getOtherAllowAddMulti().value;
		entity.otherAllowDelMulti = domain.getOtherAllowDelMulti() == null? null: domain.getOtherAllowDelMulti().value;
		return entity;

	}

	@Override
	public void add(PersonInfoCategoryAuth domain) {
		this.commandProxy().insert(toEntity(domain));
		this.getEntityManager().flush();

	}

	@Override
	public void update(PersonInfoCategoryAuth domain) {

		Optional<PpemtPersonCategoryAuth> opt = this.queryProxy().find(
				new PpemtPersonCategoryAuthPk(domain.getRoleId(), domain.getPersonInfoCategoryAuthId()),
				PpemtPersonCategoryAuth.class);

		if (opt.isPresent()) {

			this.commandProxy().update(opt.get().updateFromDomain(domain));
		}

	}

	@Override
	public void delete(String roleId, String personCategoryAuthId) {
		this.commandProxy().remove(PpemtPersonCategoryAuth.class,
				new PpemtPersonCategoryAuthPk(roleId, personCategoryAuthId));
	}

	@Override
	public Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuthByPId(String roleId,
			String personCategoryAuthId) {
		return this.queryProxy()
				.find(new PpemtPersonCategoryAuthPk(roleId, personCategoryAuthId), PpemtPersonCategoryAuth.class)
				.map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public Map<String, PersonInfoCategoryAuth> getByRoleIdAndCategories(String roleId, List<String> categoryIdList) {
		
		if  (categoryIdList.isEmpty()) {
			return new HashMap<>();
		}
		
		return this.queryProxy().query(SEE_BY_ROLEID_AND_CTG_ID_LIST, PpemtPersonCategoryAuth.class)
				.setParameter("roleId", roleId).setParameter("categoryIdList", categoryIdList).getList().stream()
				.collect(Collectors.toMap(c -> c.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId, c -> toDomain(c)));
	}
	
	@Override
	public Map<String, PersonInfoCategoryAuth> getByRoleId(String roleId) {

		return this.queryProxy().query(SEL_CATEGORY_BY_ROLEID, PpemtPersonCategoryAuth.class)
				.setParameter("roleId", roleId).getList().stream()
				.map(ent -> toDomain(ent))
				.collect(Collectors.toMap(c -> c.getPersonInfoCategoryAuthId(), c -> c));
	}

	@Override
	public List<PersonInfoCategoryDetail> getAllCategory(String roleId, String contractCd, String companyId, int salaryUseAtr, int personnelUseAtr, int employmentUseAtr ) {
		
		String SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY =String.join(" ","SELECT DISTINCT c.ppemtPerInfoCtgPK.perInfoCtgId, c.categoryCd, c.categoryName, ",
				" cm.categoryType, p.allowPersonRef, p.allowOtherRef, cm.personEmployeeType,",
				" CASE WHEN p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId IS NOT NULL  THEN 'True' ELSE 'False' END AS IsConfig,",
				"(select count(ii) from PpemtPerInfoItem ii where ii.perInfoCtgId=c.ppemtPerInfoCtgPK.perInfoCtgId and  ii.abolitionAtr =0) as count_i ,",
				"(select count(ia) from PpemtPersonItemAuth ia where ia.ppemtPersonItemAuthPk.personInfoCategoryAuthId=c.ppemtPerInfoCtgPK.perInfoCtgId and ia.ppemtPersonItemAuthPk.roleId=p.ppemtPersonCategoryAuthPk.roleId) as count_ia",
				" FROM PpemtPerInfoCtg c" + " INNER JOIN PpemtPerInfoCtgCm cm",
				" ON c.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd",
				 " AND cm.ppemtPerInfoCtgCmPK.contractCd = :contractCd" ,
				 " INNER JOIN PpemtPerInfoCtgOrder co",
				 "	ON c.ppemtPerInfoCtgPK.perInfoCtgId = co.ppemtPerInfoCtgPK.perInfoCtgId",
				 " INNER JOIN PpemtPerInfoItem i" + " ON  c.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId",
				 " LEFT JOIN PpemtPersonCategoryAuth p ",
				 " ON p.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId  = c.ppemtPerInfoCtgPK.perInfoCtgId",
				 " AND p.ppemtPersonCategoryAuthPk.roleId = :roleId" + " WHERE c.cid = :companyId",
				 " AND c.abolitionAtr = 0");
		String CONDITION = null,
			   salaryString ="",
			   personnelString = "",
			   employmentString ="";

		if (salaryUseAtr == NotUseAtr.USE.value) {
			
			CONDITION = CONDITION == null ? "AND (cm.salaryUseAtr = 1" : "OR cm.salaryUseAtr = 1";
			salaryString = CONDITION;

		}

		if (personnelUseAtr == NotUseAtr.USE.value) {
			
			CONDITION = CONDITION == null ? " AND (cm.personnelUseAtr = 1" : "OR cm.personnelUseAtr = 1";
			personnelString = CONDITION;

		} 
		
		if (employmentUseAtr == NotUseAtr.USE.value) {
			
			CONDITION = CONDITION == null ? "AND (cm.employmentUseAtr = 1" : "OR cm.employmentUseAtr = 1";
			employmentString = CONDITION;
			
		}
		
		if( !salaryString.equals("") || !personnelString.equals("") || !employmentString.equals("")){
			
			CONDITION =  String.join(" ", salaryString, personnelString, employmentString, ")");
			
		}else{
			
			CONDITION =  String.join(" ", salaryString, personnelString, employmentString);
		}
		
		SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY  = String.join(" ", SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY, CONDITION ,"ORDER BY co.disporder");
		
		return this.queryProxy().query(SELECT_CATEGORY_BY_PERSON_ROLE_ID_QUERY, Object[].class)
				.setParameter("roleId", roleId).setParameter("contractCd", contractCd)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoCategoryAuth> getAllCategoryAuthByRoleId(String roleId) {
		return this.queryProxy().query(SEL_CATEGORY_BY_ROLEID, PpemtPersonCategoryAuth.class)
				.setParameter("roleId", roleId).getList(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoCategoryDetail> getAllCategoryInfo() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SEL_CATEGORY_BY_ABOLITION_ATR, Object[].class).setParameter("CID", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoCategoryDetail> getAllCategoryByRoleId(String roleId) {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SEL_ALL_CATEGORY, Object[].class).setParameter("roleId", roleId)
				.setParameter("CID", companyId).getList(c -> toDomain(c));
	}

	@Override
	public void deleteByRoleId(String roleId) {
		this.getEntityManager().createQuery(DEL_BY_ROLE_ID).setParameter("roleId", roleId).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public List<PersonInfoCategoryDetail> getAllCategoryByCtgIdList(String companyId, List<String> perInfoCtgIdlst) {

		return this.queryProxy().query(SELECT_CATEGORY_BY_CATEGORY_LIST_ID_QUERY, Object[].class)
				.setParameter("companyId", companyId).setParameter("perInfoCtgIdlst", perInfoCtgIdlst)
				.getList(c -> toDomainLess(c));

	}

}
