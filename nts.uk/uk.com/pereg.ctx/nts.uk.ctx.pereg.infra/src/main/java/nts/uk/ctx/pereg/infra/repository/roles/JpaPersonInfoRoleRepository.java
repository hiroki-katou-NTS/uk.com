package nts.uk.ctx.pereg.infra.repository.roles;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.PersonInforRole;
import nts.uk.ctx.pereg.dom.roles.PersonInforRoleRepository;
import nts.uk.ctx.pereg.infra.entity.roles.PpemtPersonRole;
import nts.uk.ctx.pereg.infra.entity.roles.PpemtPersonRolePk;

@Stateless
public class JpaPersonInfoRoleRepository extends JpaRepository implements PersonInforRoleRepository {
	/*private final String SELECT_NO_WHERE = "SELECT c FROM PpemtPersonRole c";
	
	private final String SELECT_ROLE_BY_COMPANY_ID = SELECT_NO_WHERE
			+" WHERE c.companyId =:companyId ORDER BY c.roleCode";

	private final String SEL_2 = SELECT_NO_WHERE 
			+ " WHERE c.companyId =:companyId AND c.ppemtPersonRolePk.roleId =:roleId ";*/

//	private static PersonInforRole toDomain(PpemtPersonRole entity) {
//		val domain = PersonInforRole.createFromJavaType(entity.companyId, entity.ppemtPersonRolePk.roleId,
//				entity.roleCode, entity.roleName);
//		return domain;
//	}

	private static PpemtPersonRole toEntity(PersonInforRole domain) {
		PpemtPersonRole entity = new PpemtPersonRole();
		entity.ppemtPersonRolePk = new PpemtPersonRolePk(domain.getRoleId());
		entity.companyId = domain.getCompanyId();
		entity.roleCode = domain.getRoleCode().v();
		entity.roleName = domain.getRoleName().v();
		return entity;

	}

	@Override
	public List<PersonInforRole> getAllPersonRole() {
//		String companyId = AppContexts.user().companyId();
		return null;/* this.queryProxy().query(SELECT_ROLE_BY_COMPANY_ID, PpemtPersonRole.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));*/
	}

	@Override
	public Optional<PersonInforRole> getDetailPersonRole(String roleId, String companyId) {
		return null;/* this.queryProxy().query(SEL_2, PpemtPersonRole.class).setParameter("roleId", roleId)
				.setParameter("companyId", companyId).getSingle().map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());*/
	}

	@Override
	public void add(PersonInforRole domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PersonInforRole domain) {
		try {
			this.commandProxy().update(toEntity(domain));
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void delete(String roleId) {
		this.commandProxy().remove(PpemtPersonRole.class, new PpemtPersonRolePk(roleId));
	}

}
