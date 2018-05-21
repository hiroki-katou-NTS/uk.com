package nts.uk.ctx.pereg.infra.repository.roles.auth.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemDetail;
import nts.uk.ctx.pereg.infra.entity.roles.auth.item.PpemtPersonItemAuth;
import nts.uk.ctx.pereg.infra.entity.roles.auth.item.PpemtPersonItemAuthPk;

@Stateless
public class JpaPersonInfoItemAuthRepository extends JpaRepository implements PersonInfoItemAuthRepository {

	private final String SELECT_ITEM_INFO_AUTH_BY_CATEGORY_ID_QUERY = "SELECT DISTINCT p.ppemtPersonItemAuthPk.roleId,"
			+ " p.ppemtPersonItemAuthPk.personInfoCategoryAuthId, i.ppemtPerInfoItemPK.perInfoItemDefId, p.selfAuthType,"
			+ " p.otherPersonAuthType, i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " CASE WHEN p.ppemtPersonItemAuthPk.personItemDefId IS NULL THEN 'False' ELSE 'True' END AS IsConfig, im.itemParentCd, im.dataType" 
			+ " FROM PpemtPerInfoItem i"
			+ " INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm im ON i.itemCd = im.ppemtPerInfoItemCmPK.itemCd"
			+ " AND im.ppemtPerInfoItemCmPK.categoryCd = c.categoryCd AND im.ppemtPerInfoItemCmPK.contractCd = :contractCd" 
			+ " INNER JOIN PpemtPerInfoItemOrder io ON i.ppemtPerInfoItemPK.perInfoItemDefId = io.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " AND i.perInfoCtgId = :personInfoCategoryAuthId" 
			+ " LEFT JOIN PpemtPersonItemAuth p ON i.ppemtPerInfoItemPK.perInfoItemDefId = p.ppemtPersonItemAuthPk.personItemDefId"
			+ " AND p.ppemtPersonItemAuthPk.personInfoCategoryAuthId =:personInfoCategoryAuthId AND p.ppemtPersonItemAuthPk.roleId =:roleId" 
			+ " WHERE i.abolitionAtr = 0" + " ORDER BY io.disporder";

	private final String SEL_ALL_ITEM_AUTH_BY_ROLE_ID_CTG_ID = " SELECT c FROM PpemtPersonItemAuth c"
			+ " WHERE c.ppemtPersonItemAuthPk.roleId =:roleId"
			+ " AND c.ppemtPersonItemAuthPk.personInfoCategoryAuthId =:categoryId ";

	private final String SEL_ALL_BY_ROLE_ID_CTG_ID_LIST = " SELECT c FROM PpemtPersonItemAuth c"
			+ " WHERE c.ppemtPersonItemAuthPk.roleId =:roleId"
			+ " AND c.ppemtPersonItemAuthPk.personInfoCategoryAuthId IN :categoryIdList ";
	
	private final String SEL_ALL_BY_ROLE_ID = " SELECT c FROM PpemtPersonItemAuth c"
			+ " WHERE c.ppemtPersonItemAuthPk.roleId =:roleId";

	private final String DELETE_BY_ROLE_ID = "DELETE FROM PpemtPersonItemAuth c"
			+ " WHERE c.ppemtPersonItemAuthPk.roleId =:roleId";

	private final String SEL_ALL_ITEM_DATA = String.join(" ", "SELECT id.ppemtPersonItemAuthPk.personItemDefId",
			" FROM PpemtPersonItemAuth id",
			" INNER JOIN PpemtPerInfoItem pi ON id.ppemtPersonItemAuthPk.personItemDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId",
			" INNER JOIN PpemtPerInfoCtg pc ON pi.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId",
			" INNER JOIN PpemtPerInfoItemCm pm ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd",
			" WHERE pm.ppemtPerInfoItemCmPK.itemCd =:itemCd", " AND pi.perInfoCtgId IN :perInfoCtgId");
	
	private final String IS_SELF_AUTH = String.join(" ",
			" SELECT au  FROM  PpemtPerInfoCtg ca, PpemtPerInfoCtgCm co, PpemtPerInfoItem i, PpemtPerInfoItemCm ic, PpemtPersonItemAuth au",
			" WHERE ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd", 
			" AND ca.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId  AND co.ppemtPerInfoCtgCmPK.categoryCd  = ic.ppemtPerInfoItemCmPK.categoryCd ",
			" AND i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd",
			" AND co.ppemtPerInfoCtgCmPK.contractCd = ic.ppemtPerInfoItemCmPK.contractCd ",
			" AND i.ppemtPerInfoItemPK.perInfoItemDefId = au.ppemtPersonItemAuthPk.personItemDefId ",
			" AND ca.ppemtPerInfoCtgPK.perInfoCtgId = au.ppemtPersonItemAuthPk.personInfoCategoryAuthId ",
			" AND i.itemCd =:itemCd",
			" AND ca.categoryCd =:categoryCd",	
			" AND ca.cid =:cid  AND  co.ppemtPerInfoCtgCmPK.contractCd =:contractCd",
			" AND au.ppemtPersonItemAuthPk.roleId =:roleId");


	private static PpemtPersonItemAuth toEntity(PersonInfoItemAuth domain) {
		PpemtPersonItemAuth entity = new PpemtPersonItemAuth();
		entity.ppemtPersonItemAuthPk = new PpemtPersonItemAuthPk(domain.getRoleId(), domain.getPersonCategoryAuthId(),
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

	private static PersonInfoItemAuth toDomain(PpemtPersonItemAuth entity) {

		return PersonInfoItemAuth.createFromJavaType(entity.ppemtPersonItemAuthPk.roleId,
				entity.ppemtPersonItemAuthPk.personInfoCategoryAuthId, entity.ppemtPersonItemAuthPk.personItemDefId,
				entity.selfAuthType, entity.otherPersonAuthType);

	}

	@Override
	public void add(PersonInfoItemAuth domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PersonInfoItemAuth domain) {

		Optional<PpemtPersonItemAuth> opt = this.queryProxy().find(new PpemtPersonItemAuthPk(domain.getRoleId(),
				domain.getPersonCategoryAuthId(), domain.getPersonItemDefId()), PpemtPersonItemAuth.class);

		if (opt.isPresent()) {

			this.commandProxy().update(opt.get().updateFromDomain(domain));

		}

	}

	@Override
	public void delete(String roleId, String personCategoryAuthId, String personItemDefId) {
		this.commandProxy().remove(PpemtPersonItemAuth.class,
				new PpemtPersonItemAuthPk(roleId, personCategoryAuthId, personItemDefId));

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
				.find(new PpemtPersonItemAuthPk(roleId, categoryId, personItemDefId), PpemtPersonItemAuth.class)
				.map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
		return obj;
	}

	@Override
	public List<PersonInfoItemAuth> getAllItemAuth(String roleId, String categoryId) {
		return this.queryProxy().query(SEL_ALL_ITEM_AUTH_BY_ROLE_ID_CTG_ID, PpemtPersonItemAuth.class)
				.setParameter("roleId", roleId).setParameter("categoryId", categoryId).getList(c -> toDomain(c));
	}

	@Override
	public Map<String, List<PersonInfoItemAuth>> getByRoleIdAndCategories(String roleId, List<String> categoryIdList) {

		if (categoryIdList.isEmpty()) {
			return new HashMap<>();
		}

		List<PersonInfoItemAuth> itemAuthList = this.queryProxy()
				.query(SEL_ALL_BY_ROLE_ID_CTG_ID_LIST, PpemtPersonItemAuth.class).setParameter("roleId", roleId)
				.setParameter("categoryIdList", categoryIdList).getList().stream().map(ent -> toDomain(ent))
				.collect(Collectors.toList());

		return itemAuthList.stream().collect(Collectors.groupingBy(PersonInfoItemAuth::getPersonCategoryAuthId));

	}
	
	@Override
	public Map<String, List<PersonInfoItemAuth>> getByRoleId(String roleId) {

		List<PersonInfoItemAuth> itemAuthList = this.queryProxy()
				.query(SEL_ALL_BY_ROLE_ID, PpemtPersonItemAuth.class).setParameter("roleId", roleId)
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
		return this.queryProxy().query(IS_SELF_AUTH, PpemtPersonItemAuth.class)
		.setParameter("roleId", roleId)
		.setParameter("categoryCd",  ctgCd)
		.setParameter("itemCd", itemCd)
		.setParameter("cid", cid)
		.setParameter("contractCd", contractCd)
		.getSingle(c -> toDomain(c));
	}

}
