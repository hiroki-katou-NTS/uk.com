package nts.uk.ctx.sys.auth.infra.repository.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;
import nts.uk.ctx.sys.auth.infra.entity.role.SaumtPersonRole;

@Stateless
public class PersonRoleRepositoryImpl extends JpaRepository implements PersonRoleRepository {

	/**
	 * JPQL: find (without where)
	 */
	private static String FIND_NO_WHERE = "SELECT e FROM SaumtPersonRole e";

	/**
	 * JPQL: find by role id
	 */
	private static String FIND_BY_ROLE_ID = FIND_NO_WHERE + " WHERE e.roleId = :roleId ";

	/**
	 * JPQL: find by list role id
	 */
	private static String FIND_BY_LIST_ROLE_ID = FIND_NO_WHERE + " WHERE e.roleId IN :roleIds ";

	@Override
	public Optional<PersonRole> find(String roleId) {
		SaumtPersonRole entity = this.queryProxy().query(FIND_BY_ROLE_ID, SaumtPersonRole.class)
				.setParameter("roleId", roleId).getSingleOrNull();
		PersonRole domain = new PersonRole();
		if (entity != null) {
			domain = toDomain(entity);
		}
		return Optional.of(domain);
	}

	private static PersonRole toDomain(SaumtPersonRole entity) {
		PersonRole domain = new PersonRole();
		domain.setRoleId(entity.getRoleId());
		domain.setReferFutureDate(entity.isReferFutureDate());
		return domain;
	}

	@Override
	public List<PersonRole> find(List<String> roleIds) {
		List<PersonRole> result = new ArrayList<>();
		List<SaumtPersonRole> entitys = this.queryProxy().query(FIND_BY_LIST_ROLE_ID, SaumtPersonRole.class)
				.setParameter("roleIds", roleIds).getList();
		if (entitys != null && entitys.size() == 0)
			result = entitys.stream().map(x -> toDomain(x)).collect(Collectors.toList());
		return result;
	}

	@Override
	public void insert(PersonRole personRole) {
		this.commandProxy().insert(toEntity(personRole));
	}

	@Override
	public void update(PersonRole personRole) {
			this.commandProxy().update(toEntity(personRole));
	}

	@Override
	public void remove(String roleId) {	
			this.commandProxy().remove(SaumtPersonRole.class, roleId);
	}

	
	private static SaumtPersonRole  toEntity(PersonRole personRole){
		SaumtPersonRole entity = new SaumtPersonRole();
		entity.setRoleId(personRole.getRoleId());
		entity.setReferFutureDate(personRole.getReferFutureDate());
		return entity;
	}
}
