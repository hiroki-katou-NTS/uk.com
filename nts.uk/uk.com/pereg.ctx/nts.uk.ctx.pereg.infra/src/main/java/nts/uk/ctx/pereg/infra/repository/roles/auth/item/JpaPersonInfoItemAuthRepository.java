package nts.uk.ctx.pereg.infra.repository.roles.auth.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemDetail;
import nts.uk.ctx.pereg.infra.entity.roles.auth.item.PpemtRoleItemAuth;
import nts.uk.ctx.pereg.infra.entity.roles.auth.item.PpemtRoleItemAuthPk;

@Stateless
public class JpaPersonInfoItemAuthRepository extends JpaRepository implements PersonInfoItemAuthRepository {
	
	private static final String IS_SELF_AUTH =
			"SELECT au  FROM  PpemtCtg ca INNER JOIN  PpemtCtgCommon co ON ca.categoryCd = co.ppemtCtgCommonPK.categoryCd AND co.ppemtCtgCommonPK.categoryCd =:categoryCd AND ca.cid =:cid  AND co.ppemtCtgCommonPK.contractCd =:contractCd"
			+ " INNER JOIN PpemtItem i ON  ca.ppemtCtgPK.perInfoCtgId = i.perInfoCtgId"
			+ " INNER JOIN PpemtItemCommon ic "
			+ " ON i.itemCd = ic.ppemtItemCommonPK.itemCd AND co.ppemtCtgCommonPK.contractCd = ic.ppemtItemCommonPK.contractCd"
			+ " AND co.ppemtCtgCommonPK.categoryCd = ic.ppemtItemCommonPK.categoryCd AND ic.ppemtItemCommonPK.itemCd =:itemCd"
			+ " INNER JOIN PpemtRoleItemAuth au ON i.ppemtItemPK.perInfoItemDefId = au.ppemtRoleItemAuthPk.personItemDefId"
			+ " AND ca.ppemtCtgPK.perInfoCtgId = au.ppemtRoleItemAuthPk.personInfoCategoryAuthId AND au.ppemtRoleItemAuthPk.roleId =:roleId";


	private static final String SELECT_ITEM_INFO_AUTH_BY_CATEGORY_ID_QUERY = "SELECT DISTINCT p.ppemtRoleItemAuthPk.roleId,"
			+ " p.ppemtRoleItemAuthPk.personInfoCategoryAuthId, i.ppemtItemPK.perInfoItemDefId, p.selfAuthType,"
			+ " p.otherPersonAuthType, i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " CASE WHEN p.ppemtRoleItemAuthPk.personItemDefId IS NULL THEN 'False' ELSE 'True' END AS IsConfig, im.itemParentCd, im.dataType" 
			+ " FROM PpemtItem i"
			+ " INNER JOIN PpemtCtg c ON i.perInfoCtgId = c.ppemtCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtItemCommon im ON i.itemCd = im.ppemtItemCommonPK.itemCd"
			+ " AND im.ppemtItemCommonPK.categoryCd = c.categoryCd AND im.ppemtItemCommonPK.contractCd = :contractCd" 
			+ " INNER JOIN PpemtItemSort io ON i.ppemtItemPK.perInfoItemDefId = io.ppemtItemPK.perInfoItemDefId"
			+ " AND i.perInfoCtgId = :personInfoCategoryAuthId" 
			+ " LEFT JOIN PpemtRoleItemAuth p ON i.ppemtItemPK.perInfoItemDefId = p.ppemtRoleItemAuthPk.personItemDefId"
			+ " AND p.ppemtRoleItemAuthPk.personInfoCategoryAuthId =:personInfoCategoryAuthId AND p.ppemtRoleItemAuthPk.roleId =:roleId" 
			+ " WHERE i.abolitionAtr = 0" + " ORDER BY io.disporder";

	private static final String SEL_ALL_ITEM_AUTH_BY_ROLE_ID_CTG_ID = " SELECT c FROM PpemtRoleItemAuth c"
			+ " WHERE c.ppemtRoleItemAuthPk.roleId =:roleId"
			+ " AND c.ppemtRoleItemAuthPk.personInfoCategoryAuthId =:categoryId ";

	private static final String SEL_ALL_BY_ROLE_ID_CTG_ID_LIST = " SELECT c FROM PpemtRoleItemAuth c"
			+ " WHERE c.ppemtRoleItemAuthPk.roleId =:roleId"
			+ " AND c.ppemtRoleItemAuthPk.personInfoCategoryAuthId IN :categoryIdList ";
	
	private static final String SEL_ALL_BY_ROLE_ID = " SELECT c FROM PpemtRoleItemAuth c"
			+ " WHERE c.ppemtRoleItemAuthPk.roleId =:roleId";

	private static final String DELETE_BY_ROLE_ID = "DELETE FROM PpemtRoleItemAuth c"
			+ " WHERE c.ppemtRoleItemAuthPk.roleId =:roleId";

	private static final String SEL_ALL_ITEM_DATA = "SELECT id.ppemtRoleItemAuthPk.personItemDefId"
			+ " FROM PpemtRoleItemAuth id"
			+ " INNER JOIN PpemtItem pi ON id.ppemtRoleItemAuthPk.personItemDefId = pi.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtCtg pc ON pi.perInfoCtgId = pc.ppemtCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtItemCommon pm ON pi.itemCd = pm.ppemtItemCommonPK.itemCd AND pc.categoryCd = pm.ppemtItemCommonPK.categoryCd"
			+ " WHERE pm.ppemtItemCommonPK.itemCd =:itemCd"
			+ " AND pi.perInfoCtgId IN :perInfoCtgId";
	
	private static final String SEL_ALL_ITEM_AUTH_BY_ROLE_ID_CTG_ID_ITEM_ID = " SELECT c FROM PpemtRoleItemAuth c"
			+ " WHERE c.ppemtRoleItemAuthPk.roleId =:roleId"
			+ " AND c.ppemtRoleItemAuthPk.personInfoCategoryAuthId =:categoryId"
			+ " AND c.ppemtRoleItemAuthPk.personItemDefId IN :itemIds"
			+ " AND (c.otherPersonAuthType =:otherType OR c.selfAuthType =:selfType)";
	


	private static PpemtRoleItemAuth toEntity(PersonInfoItemAuth domain) {
		PpemtRoleItemAuth entity = new PpemtRoleItemAuth();
		entity.ppemtRoleItemAuthPk = new PpemtRoleItemAuthPk(domain.getRoleId(), domain.getPersonCategoryAuthId(),
				domain.getPersonItemDefId());
		entity.otherPersonAuthType = domain.getOtherAuth().value;
		entity.selfAuthType = domain.getSelfAuth().value;
		return entity;

	}

	private static PersonInfoItemDetail createPersonInfoItemDetail(Object[] entity) {

		val itemDetail = new PersonInfoItemDetail();

		itemDetail.setRoleId(entity[0] == null ? "a" : entity[0].toString());

		itemDetail.setPersonInfoCategoryAuthId(entity[1] == null ? "a" : entity[1].toString());

		itemDetail.setPersonItemDefId(entity[2] == null ? "a" : entity[2].toString());

		itemDetail.setSelfAuthType(entity[3] == null ? 9 : Integer.valueOf(entity[3].toString()));

		itemDetail.setOtherPersonAuth(entity[4] == null ? 9 : Integer.valueOf(entity[4].toString()));

		itemDetail.setItemCd(entity[5] == null ? "a" : entity[5].toString());

		itemDetail.setItemName(entity[6] == null ? "a" : entity[6].toString());

		itemDetail.setAbolitionAtr(entity[7] == null ? 9 : Integer.valueOf(entity[7].toString()));

		itemDetail.setRequiredAtr(entity[8] == null ? 9 : Integer.valueOf(entity[8].toString()));

		itemDetail.setSetting(entity[9] == null ? false : Boolean.valueOf(entity[9].toString()));

		itemDetail.setItemParentCd(entity[10] == null ? null : entity[10].toString());
		
		itemDetail.setDataType(entity[11] == null ? 1 : Integer.valueOf(entity[11].toString()));

		return itemDetail;
	}

	private static PersonInfoItemAuth toDomain(PpemtRoleItemAuth entity) {

		return PersonInfoItemAuth.createFromJavaType(entity.ppemtRoleItemAuthPk.roleId,
				entity.ppemtRoleItemAuthPk.personInfoCategoryAuthId, entity.ppemtRoleItemAuthPk.personItemDefId,
				entity.selfAuthType, entity.otherPersonAuthType);

	}

	@Override
	public void add(PersonInfoItemAuth domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PersonInfoItemAuth domain) {

		Optional<PpemtRoleItemAuth> opt = this.queryProxy().find(new PpemtRoleItemAuthPk(domain.getRoleId(),
				domain.getPersonCategoryAuthId(), domain.getPersonItemDefId()), PpemtRoleItemAuth.class);

		if (opt.isPresent()) {

			this.commandProxy().update(opt.get().updateFromDomain(domain));

		}

	}

	@Override
	public void delete(String roleId, String personCategoryAuthId, String personItemDefId) {
		this.commandProxy().remove(PpemtRoleItemAuth.class,
				new PpemtRoleItemAuthPk(roleId, personCategoryAuthId, personItemDefId));

	}

	@Override
	public List<PersonInfoItemDetail> getAllItemDetail(String roleId, String personInfoCategoryAuthId,
			String contractCd) {
		return this.queryProxy().query(SELECT_ITEM_INFO_AUTH_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("personInfoCategoryAuthId", personInfoCategoryAuthId).setParameter("roleId", roleId)
				.setParameter("contractCd", contractCd).getList(c -> createPersonInfoItemDetail(c));
	}

	@Override
	public Optional<PersonInfoItemAuth> getItemDetai(String roleId, String categoryId, String personItemDefId) {

		Optional<PersonInfoItemAuth> obj = this.queryProxy()
				.find(new PpemtRoleItemAuthPk(roleId, categoryId, personItemDefId), PpemtRoleItemAuth.class)
				.map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
		return obj;
	}

	@Override
	public List<PersonInfoItemAuth> getAllItemAuth(String roleId, String categoryId) {
		return this.queryProxy().query(SEL_ALL_ITEM_AUTH_BY_ROLE_ID_CTG_ID, PpemtRoleItemAuth.class)
				.setParameter("roleId", roleId).setParameter("categoryId", categoryId).getList(c -> toDomain(c));
	}

	@Override
	public Map<String, List<PersonInfoItemAuth>> getByRoleIdAndCategories(String roleId, List<String> categoryIdList) {

		if (categoryIdList.isEmpty()) {
			return new HashMap<>();
		}

		List<PersonInfoItemAuth> itemAuthList = this.queryProxy()
				.query(SEL_ALL_BY_ROLE_ID_CTG_ID_LIST, PpemtRoleItemAuth.class).setParameter("roleId", roleId)
				.setParameter("categoryIdList", categoryIdList).getList().stream().map(ent -> toDomain(ent))
				.collect(Collectors.toList());

		return itemAuthList.stream().collect(Collectors.groupingBy(PersonInfoItemAuth::getPersonCategoryAuthId));

	}
	
	@Override
	public Map<String, List<PersonInfoItemAuth>> getByRoleId(String roleId) {

		List<PersonInfoItemAuth> itemAuthList = this.queryProxy()
				.query(SEL_ALL_BY_ROLE_ID, PpemtRoleItemAuth.class).setParameter("roleId", roleId)
				.getList().stream().map(ent -> toDomain(ent))
				.collect(Collectors.toList());

		return itemAuthList.stream().collect(Collectors.groupingBy(PersonInfoItemAuth::getPersonCategoryAuthId));

	}

	@Override
	public void deleteByRoleId(String roleId) {
		this.getEntityManager().createQuery(DELETE_BY_ROLE_ID).setParameter("roleId", roleId).executeUpdate();
		this.getEntityManager().flush();

	}

	@Override
	public boolean hasItemData(String itemCd, List<String> perInfoCtgId) {
		// SEL_ALL_ITEM_DATA
		List<String> itemList = this.queryProxy().query(SEL_ALL_ITEM_DATA, String.class).setParameter("itemCd", itemCd)
				.setParameter("perInfoCtgId", perInfoCtgId).getList();
		if (itemList.size() > 0) {

			return true;
		}
		return false;
	}

	@Override
	public Optional<PersonInfoItemAuth> getItemAuth(String roleId, String ctgCd, String cid, String contractCd, String itemCd) {
		return this.queryProxy().query(IS_SELF_AUTH, PpemtRoleItemAuth.class)
		.setParameter("roleId", roleId)
		.setParameter("categoryCd",  ctgCd)
		.setParameter("itemCd", itemCd)
		.setParameter("cid", cid)
		.setParameter("contractCd", contractCd)
		.getSingle(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoItemAuth> getAllItemAuth(String roleId, String categoryId, List<String> itemIds) {
		return this.queryProxy().query(SEL_ALL_ITEM_AUTH_BY_ROLE_ID_CTG_ID_ITEM_ID, PpemtRoleItemAuth.class)
				.setParameter("roleId", roleId)
				.setParameter("categoryId", categoryId)
				.setParameter("itemIds", itemIds)
				.setParameter("otherType", PersonInfoAuthType.UPDATE.value)
				.setParameter("selfType", PersonInfoAuthType.UPDATE.value)
				.getList(c -> toDomain(c));
	}

}
