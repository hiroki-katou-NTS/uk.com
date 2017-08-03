package repository.roles;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import entity.roles.auth.item.PpemtPersonItemAuth;
import entity.roles.auth.item.PpemtPersonItemAuthPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemDetail;

@Stateless
public class JpaPersonInfoItemAuthRepository extends JpaRepository implements PersonInfoItemAuthRepository {

	private final String SEL_NO_WHERE = "SELECT c FROM PpemtPersonItemAuth c";

	private final String SEL_1 = SEL_NO_WHERE + " WHERE c.ppemtPersonItemAuthPk.roleId =:roleId "
			+ " AND c.ppemtPersonItemAuthPk.personInfoCategoryAuthId =:personInfoCategoryAuthId ";

	private final String SEL_2 = SEL_1 + " AND c.ppemtPersonItemAuthPk.personItemDefId =: personItemDefId";

	private final String SEL_3 = " SELECT p.ppemtPersonItemAuthPk.roleId, p.ppemtPersonItemAuthPk.personInfoCategoryAuthId,"
			+ " p.ppemtPersonItemAuthPk.personItemDefId,"
			+ " p.selfAuthType, p.otherPersonAuth, c.itemCd, c.itemName, c.abolitionAtr, c.requiredAtr "
			+ "FROM PpemtPerInfoItem c " + " LEFT JOIN PpemtPersonItemAuth p"
			+ " ON c.ppemtPerInfoItemPK.perInfoItemDefinitionId = p.ppemtPersonItemAuthPk.personItemDefId"
			+ " AND p.ppemtPersonItemAuthPk.personInfoCategoryAuthId =:personInfoCategoryAuthId ";

	private static PersonInfoItemAuth toDomain(PpemtPersonItemAuth entity) {
		val domain = PersonInfoItemAuth.createFromJavaType(entity.ppemtPersonItemAuthPk.roleId,
				entity.ppemtPersonItemAuthPk.personInfoCategoryAuthId, entity.ppemtPersonItemAuthPk.personItemDefId,
				entity.selfAuthType, entity.otherPersonAuth);
		return domain;
	}

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
		if(entity[0]==null){
			domain.setRoleId("a");
		}else{
			domain.setRoleId(entity[0].toString());
		}
		if(entity[1] == null){
			domain.setPersonInfoCategoryAuthId("a");
		}else{
			domain.setPersonInfoCategoryAuthId(entity[1].toString());
			
		}
		if(entity[2] == null){
			domain.setPersonItemDefId("a");
		}else{
			domain.setPersonItemDefId(entity[2].toString());
		}
		if(entity[3] == null){
			domain.setOtherPersonAuth(9);
		}else{
			domain.setOtherPersonAuth(Integer.valueOf(entity[3].toString()));
		}
		if(entity[4] ==null){
			domain.setSelfAuthType(9);
		}else{
			domain.setSelfAuthType(Integer.valueOf(entity[4].toString()));
		}
		if(entity[5] ==null){
			domain.setItemCd("a");
		}else{
			domain.setItemCd(entity[5].toString());
			
		}
		if(entity[6]== null){
			domain.setItemName("a");
		}else{
			domain.setItemName(entity[6].toString());
		}
		if(entity[7]==null){
			domain.setAbolitionAtr(9);
		}else{
			domain.setAbolitionAtr(Integer.valueOf(entity[7].toString()));
		}
		if(entity[8]==null){
			domain.setRequiredAtr(9);
		}else{
			domain.setRequiredAtr(Integer.valueOf(entity[8].toString()));
			
		}

		return domain;
	}

	@Override
	public List<PersonInfoItemAuth> getAllPersonItemAuth() {
		return this.queryProxy().query(SEL_NO_WHERE, PpemtPersonItemAuth.class).getList(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoItemAuth> getAllPersonItemAuthByCategory(String roleId, String personCategoryAuthId) {
		return this.queryProxy().query(SEL_1, PpemtPersonItemAuth.class).setParameter("roleId", roleId)
				.setParameter("personInfoCategoryAuthId", personCategoryAuthId).getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoItemAuth> getDetailPersonItemAuth(String roleId, String personCategoryAuthId,
			String personItemDefId) {
		return this.queryProxy().query(SEL_2, PpemtPersonItemAuth.class).setParameter("roleId", roleId)
				.setParameter("personInfoCategoryAuthId", personCategoryAuthId)
				.setParameter("personItemDefId", personItemDefId).getSingle().map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
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
	public List<PersonInfoItemDetail> getAllItemDetail(String personInfoCategoryAuthId) {
		List<PersonInfoItemDetail> x = this.queryProxy().query(SEL_3, Object[].class)
				.setParameter("personInfoCategoryAuthId", personInfoCategoryAuthId).getList(c -> toDomain(c));
		return x;
	}

}
