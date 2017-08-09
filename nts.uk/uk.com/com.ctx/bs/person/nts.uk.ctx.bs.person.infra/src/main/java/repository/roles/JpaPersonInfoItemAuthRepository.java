package repository.roles;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.roles.auth.item.PpemtPersonItemAuth;
import entity.roles.auth.item.PpemtPersonItemAuthPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemDetail;

@Stateless
public class JpaPersonInfoItemAuthRepository extends JpaRepository implements PersonInfoItemAuthRepository {

	private final String SELECT_ITEM_INFO_AUTH_BY_CATEGORY_ID_QUERY = " SELECT p.ppemtPersonItemAuthPk.roleId, p.ppemtPersonItemAuthPk.personInfoCategoryAuthId,"
			+ " i.ppemtPerInfoItemPK.perInfoItemDefId,"
			+ " p.selfAuthType, p.otherPersonAuth, i.itemCd, i.itemName, i.abolitionAtr, i.requiredAtr,"
			+ " CASE WHEN p.ppemtPersonItemAuthPk.personItemDefId IS NULL THEN 'False' ELSE 'True' END AS IsConfig"
			+ " FROM PpemtPerInfoItem i "
			+ "	INNER JOIN PpemtPerInfoItemCm im"
			+ " ON i.itemCd = im.ppemtPerInfoItemCmPK.itemCd"
			+ " AND im.ppemtPerInfoItemCmPK.contractCd = :contractCd"
			+ " INNER JOIN PpemtPerInfoItemOrder io"
			+ " ON i.ppemtPerInfoItemPK.perInfoItemDefId = io.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " AND i.perInfoCtgId = :personInfoCategoryAuthId" 
			+ " LEFT JOIN PpemtPersonItemAuth p"
			+ " ON i.ppemtPerInfoItemPK.perInfoItemDefId = p.ppemtPersonItemAuthPk.personItemDefId"
			+ " AND p.ppemtPersonItemAuthPk.personInfoCategoryAuthId =:personInfoCategoryAuthId"
			+ " AND p.ppemtPersonItemAuthPk.roleId =:roleId"
			+ " WHERE i.abolitionAtr = 1"
			+ " ORDER BY io.disporder";

	private static PpemtPersonItemAuth toEntity(PersonInfoItemAuth domain) {
		PpemtPersonItemAuth entity = new PpemtPersonItemAuth();
		entity.ppemtPersonItemAuthPk = new PpemtPersonItemAuthPk(domain.getRoleId(), domain.getPersonCategoryAuthId(),
				domain.getPersonItemDefId());
		entity.otherPersonAuth = domain.getOtherAuth().value;
		entity.selfAuthType = domain.getSelfAuth().value;
		return entity;

	}

	private static PersonInfoItemDetail toDomain(Object[] entity) {

		val domain = new PersonInfoItemDetail();

		domain.setRoleId(entity[0] == null ? "a" : entity[0].toString());

		domain.setPersonInfoCategoryAuthId(entity[1] == null ? "a" : entity[1].toString());

		domain.setPersonItemDefId(entity[2] == null ? "a" : entity[2].toString());

		domain.setSelfAuthType(entity[3] == null ? 9 : Integer.valueOf(entity[3].toString()));

		domain.setOtherPersonAuth(entity[4] == null ? 9 : Integer.valueOf(entity[4].toString()));

		domain.setItemCd(entity[5] == null ? "a" : entity[5].toString());

		domain.setItemName(entity[6] == null ? "a" : entity[6].toString());

		domain.setAbolitionAtr(entity[7] == null ? 9 : Integer.valueOf(entity[7].toString()));

		domain.setRequiredAtr(entity[8] == null ? 9 : Integer.valueOf(entity[8].toString()));

		domain.setSetting(entity[9] == null ? false : Boolean.valueOf(entity[9].toString()));

		return domain;
	}

	private static PersonInfoItemAuth toDomain(PpemtPersonItemAuth entity) {

		return PersonInfoItemAuth.createFromJavaType(entity.ppemtPersonItemAuthPk.roleId,
				entity.ppemtPersonItemAuthPk.personInfoCategoryAuthId, entity.ppemtPersonItemAuthPk.personItemDefId,
				entity.selfAuthType, entity.otherPersonAuth);

	}

	@Override
	public void add(PersonInfoItemAuth domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PersonInfoItemAuth domain) {
		this.commandProxy().update(toEntity(domain));

	}

	@Override
	public void delete(String roleId, String personCategoryAuthId, String personItemDefId) {
		this.commandProxy().remove(PpemtPersonItemAuth.class,
				new PpemtPersonItemAuthPk(roleId, personCategoryAuthId, personItemDefId));

	}

	@Override
	public List<PersonInfoItemDetail> getAllItemDetail(String roleId, String personInfoCategoryAuthId,String contractCd) {
		List<PersonInfoItemDetail> x = this.queryProxy()
				.query(SELECT_ITEM_INFO_AUTH_BY_CATEGORY_ID_QUERY, Object[].class)
				.setParameter("personInfoCategoryAuthId", personInfoCategoryAuthId)
				.setParameter("roleId", roleId)
				.setParameter("contractCd", contractCd)
				.getList(c -> toDomain(c));
		return x;
	}

	@Override
	public Optional<PersonInfoItemAuth> getItemDetai(String roleId, String categoryId, String personItemDefId) {

		return this.queryProxy()
				.find(new PpemtPersonItemAuthPk(roleId, categoryId, personItemDefId), PpemtPersonItemAuth.class)
				.map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
	}

}
