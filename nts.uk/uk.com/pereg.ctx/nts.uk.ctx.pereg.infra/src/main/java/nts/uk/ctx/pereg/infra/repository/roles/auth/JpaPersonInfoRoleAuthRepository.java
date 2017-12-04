package nts.uk.ctx.pereg.infra.repository.roles.auth;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuth;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.pereg.infra.entity.roles.auth.PpemtPersonRoleAuth;
import nts.uk.ctx.pereg.infra.entity.roles.auth.PpemtPersonRoleAuthPk;

@Stateless
public class JpaPersonInfoRoleAuthRepository extends JpaRepository implements PersonInfoRoleAuthRepository {
	private final String SEL_NO_WHERE = "SELECT c FROM PpemtPersonRoleAuth c";

	private final String SEL_ROLE_AUTH_BY_ROLE_ID = SEL_NO_WHERE + " WHERE c.ppemtPersonRoleAuthPk.roleId=:roleId"
			+ " AND c.companyId=:companyId";

	private static PersonInfoRoleAuth toDomain(PpemtPersonRoleAuth entity) {
		val domain = PersonInfoRoleAuth.createFromJavaType(entity.ppemtPersonRoleAuthPk.roleId, entity.companyId,
				entity.allowMapUpload, entity.allowMapBrowse, entity.allowDocUpload, entity.allowDocRef,
				entity.allowAvatarUpload, entity.allowAvatarRef);
		return domain;
	}

	private static PpemtPersonRoleAuth toEntity(PersonInfoRoleAuth domain) {
		PpemtPersonRoleAuth entity = new PpemtPersonRoleAuth();
		entity.ppemtPersonRoleAuthPk = new PpemtPersonRoleAuthPk(domain.getRoleId());
		entity.companyId = domain.getCompanyId();
		entity.allowMapBrowse = domain.getAllowMapBrowse().value;
		entity.allowMapUpload = domain.getAllowMapUpload().value;
		entity.allowDocRef = domain.getAllowDocRef().value;
		entity.allowDocUpload = domain.getAllowDocUpload().value;
		entity.allowAvatarRef = domain.getAllowAvatarRef().value;
		entity.allowAvatarUpload = domain.getAllowAvatarUpload().value;
		return entity;

	}

	@Override
	public List<PersonInfoRoleAuth> getAllPersonInfoRoleAuth() {
		return this.queryProxy().query(SEL_NO_WHERE, PpemtPersonRoleAuth.class).getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoRoleAuth> getDetailPersonRoleAuth(String roleId, String companyId) {
		return this.queryProxy().query(SEL_ROLE_AUTH_BY_ROLE_ID, PpemtPersonRoleAuth.class)
				.setParameter("roleId", roleId).setParameter("companyId", companyId).getSingle().map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public void add(PersonInfoRoleAuth domain) {
		this.commandProxy().insert(toEntity(domain));
		this.getEntityManager().flush();

	}

	@Override
	public void update(PersonInfoRoleAuth domain) {

		Optional<PpemtPersonRoleAuth> opt = this.queryProxy().find(new PpemtPersonRoleAuthPk(domain.getRoleId()),
				PpemtPersonRoleAuth.class);
		if (opt.isPresent()) {

			this.commandProxy().update(opt.get().updateFromDomain(domain));

		}

	}

	@Override
	public void delete(String roleId) {
		this.commandProxy().remove(PpemtPersonRoleAuth.class, new PpemtPersonRoleAuthPk(roleId));
		this.getEntityManager().flush();
	}

}
