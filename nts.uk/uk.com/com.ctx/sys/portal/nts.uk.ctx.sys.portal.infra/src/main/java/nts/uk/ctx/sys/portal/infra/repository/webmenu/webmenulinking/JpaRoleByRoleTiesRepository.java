package nts.uk.ctx.sys.portal.infra.repository.webmenu.webmenulinking;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTies;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTiesRepository;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.webmenulinking.SacmtRoleByRoleTies;

public class JpaRoleByRoleTiesRepository extends JpaRepository implements  RoleByRoleTiesRepository {

	private static final String  GET_ROLE_BY_ROLE_TIES_BY_CODE = "SELECT c FROM SacmtRoleByRoleTies c "
			+ " WHERE c.sacmtRoleByRoleTiesPK.roleId  = :roleId ";
	
	@Override
	public void insertRoleByRoleTies(RoleByRoleTies roleByRoleTies) {
		this.commandProxy().insert(SacmtRoleByRoleTies.toEntity(roleByRoleTies));
		
	}

	@Override
	public void updateRoleByRoleTies(RoleByRoleTies roleByRoleTies) {
		SacmtRoleByRoleTies dataUpdate = SacmtRoleByRoleTies.toEntity(roleByRoleTies);
		SacmtRoleByRoleTies newData = this.queryProxy().find(dataUpdate.roleId, SacmtRoleByRoleTies.class).get();
		newData.setWebMenuCd(dataUpdate.webMenuCd);
		
	}

	@Override
	public void deleteRoleByRoleTies(String roleId) {
		this.commandProxy().remove(SacmtRoleByRoleTies.class,roleId);
	}

	@Override
	public Optional<RoleByRoleTies> getRoleByRoleTiesById(String roleId) {
		Optional<RoleByRoleTies> data = this.queryProxy().query(GET_ROLE_BY_ROLE_TIES_BY_CODE,SacmtRoleByRoleTies.class)
				.getSingle(c->c.toDomain());
		return data;
	}

}
