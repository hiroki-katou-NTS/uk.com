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

@Stateless
public class JpaPersonInfoItemAuthRepository extends JpaRepository implements PersonInfoItemAuthRepository {

	private final String SEL_NO_WHERE = "SELECT c FROM PpemtPersonItemAuth c";

	private final String SEL_1 = SEL_NO_WHERE + " WHERE c.ppemtPersonItemAuth.roleId =:roleId "
			+ " AND c.ppemtPersonItemAuth.personInfoCategoryAuthId =:personInfoCategoryAuthId ";

	private final String SEL_2 = SEL_1 + " AND c.ppemtPersonItemAuth.personItemDefId =: personItemDefId";

	private static PersonInfoItemAuth toDomain(PpemtPersonItemAuth entity) {
		val domain = PersonInfoItemAuth.createFromJavaType(
				entity.ppemtPersonItemAuthPk.roleId,
				entity.ppemtPersonItemAuthPk.personInfoCategoryAuthId, 
				entity.ppemtPersonItemAuthPk.personItemDefId, 
				entity.selfAuthType, entity.otherPersonAuth);
		return domain;
	}

	private static PpemtPersonItemAuth toEntity(PersonInfoItemAuth domain) {
		PpemtPersonItemAuth entity = new PpemtPersonItemAuth();
		entity.ppemtPersonItemAuthPk = new PpemtPersonItemAuthPk(domain.getRoleId(),
				domain.getPersonCategoryAuthId(), domain.getPersonItemDefId());
		entity.otherPersonAuth = domain.getOtherAuth().value;
		entity.selfAuthType = domain.getSelfAuth().value;
		return entity;

	}

	@Override
	public List<PersonInfoItemAuth> getAllPersonItemAuth() {
		return this.queryProxy().query(SEL_NO_WHERE,PpemtPersonItemAuth.class ).getList(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoItemAuth> getAllPersonItemAuthByCategory(String roleId, String personCategoryAuthId) {
		return this.queryProxy().query(SEL_1, PpemtPersonItemAuth.class)
				.setParameter("roleId", roleId)
				.setParameter("personInfoCategoryAuthId", personCategoryAuthId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoItemAuth> getDetailPersonItemAuth(String roleId, String personCategoryAuthId,
			String personItemDefId) {
		return this.queryProxy().query(SEL_2,PpemtPersonItemAuth.class)
				.setParameter("roleId", roleId)
				.setParameter("personInfoCategoryAuthId", personCategoryAuthId)
				.setParameter("personItemDefId", personItemDefId)
				.getSingle().map(e -> {
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
		this.commandProxy().remove(PpemtPersonItemAuth.class, new PpemtPersonItemAuthPk(roleId,personCategoryAuthId,personItemDefId));

	}

}
