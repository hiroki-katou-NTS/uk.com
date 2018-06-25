package nts.uk.ctx.pereg.infra.repository.roles.functionauth;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;
import nts.uk.ctx.pereg.infra.entity.roles.functionauth.PpemtPersonInfoAuth;
import nts.uk.shr.infra.permit.data.JpaAvailablityPermissionRepositoryBase;

@Stateless
public class PersonInfoAuthorityRepoImpl
		extends JpaAvailablityPermissionRepositoryBase<PersonInfoAuthority, PpemtPersonInfoAuth>
		implements PersonInfoAuthorityRepository {

	private static final String QUERY_COMPANY_ROLEID = "SELECT a FROM PpemtPersonInfoAuth a"
			+ " WHERE a.pk.companyId = :cid AND a.pk.roleId = :roleId";

	@Override
	public Map<Integer, PersonInfoAuthority> getListOfRole(String companyId, String roleId) {
		List<PpemtPersonInfoAuth> entities = this.queryProxy().query(QUERY_COMPANY_ROLEID, PpemtPersonInfoAuth.class)
				.setParameter("cid", companyId).setParameter("roleId", roleId).getList();
		return entities.stream().map(ent -> ent.toDomain()).collect(Collectors.toMap(x -> x.getFunctionNo(), x -> x));
	}

	@Override
	protected Class<PpemtPersonInfoAuth> getEntityClass() {
		return PpemtPersonInfoAuth.class;
	}

	@Override
	protected PpemtPersonInfoAuth createEmptyEntity() {
		return new PpemtPersonInfoAuth();
	}

	@Override
	public void delete(String companyId, String roleId) {
		List<PpemtPersonInfoAuth> entities = this.queryProxy().query(QUERY_COMPANY_ROLEID, PpemtPersonInfoAuth.class)
				.setParameter("cid", companyId).setParameter("roleId", roleId).getList();
		this.commandProxy().removeAll(entities);

	}

}
