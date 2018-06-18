package nts.uk.ctx.pereg.infra.repository.roles.functionauth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;
import nts.uk.ctx.pereg.infra.entity.roles.functionauth.PpemtPersonInfoAuth;
import nts.uk.ctx.pereg.infra.entity.roles.functionauth.PpemtPersonInfoAuthPk;

@Stateless
public class PersonInfoAuthorityRepoImpl extends JpaRepository implements PersonInfoAuthorityRepository {

	private static final String QUERY_COMPANY_ROLEID = "SELECT a FROM PpemtPersonInfoAuth a"
			+ " WHERE a.key.cid = :cid AND a.key.roleId = :roleId";

	@Override
	public Map<Integer, PersonInfoAuthority> getListOfRole(String companyId, String roleId) {
		List<PpemtPersonInfoAuth> entities = this.queryProxy()
				.query(QUERY_COMPANY_ROLEID, PpemtPersonInfoAuth.class)
				.setParameter("cid", companyId)
				.setParameter("roleId", roleId).getList();

		return entities.stream().map(ent -> toDomain(ent))
				.collect(Collectors.toMap(x -> x.getFunctionNo().v(), x -> x));

	}

	private PersonInfoAuthority toDomain(PpemtPersonInfoAuth entity) {
		return PersonInfoAuthority.createFromJavaType(entity.key.cid, entity.key.roleId, entity.key.functionNo,
				entity.available == 1);
	}
	
	private PpemtPersonInfoAuth toEntity(PersonInfoAuthority domain) {
		PpemtPersonInfoAuthPk key = new PpemtPersonInfoAuthPk(domain.getCompanyId(), domain.getRoleId(),
				domain.getFunctionNo().v());
		return new PpemtPersonInfoAuth(key, domain.isAvailable() ? 1 : 0);
	}

	public Map<Integer, PersonInfoAuthority> mock(String companyId, String roleId) {
		Map<Integer, PersonInfoAuthority> authList = new HashMap<>();
		authList.put(1, PersonInfoAuthority.createFromJavaType(companyId, roleId, 1, true));
		authList.put(2, PersonInfoAuthority.createFromJavaType(companyId, roleId, 2, true));
		authList.put(3, PersonInfoAuthority.createFromJavaType(companyId, roleId, 3, true));
		authList.put(4, PersonInfoAuthority.createFromJavaType(companyId, roleId, 4, true));
		return authList;
	}

	@Override
	public void add(PersonInfoAuthority auth) {
		this.commandProxy().insert(toEntity(auth));
	}

	@Override
	public void update(PersonInfoAuthority auth) {
		PpemtPersonInfoAuthPk key = new PpemtPersonInfoAuthPk(auth.getCompanyId(), auth.getRoleId(),
				auth.getFunctionNo().v());
		Optional<PpemtPersonInfoAuth> entity = this.queryProxy().find(key, PpemtPersonInfoAuth.class);
		
		if (!entity.isPresent()) {
			return;
		}
		
		entity.get().available = auth.isAvailable() ? 1 : 0;
		
		this.commandProxy().update(entity);
	}

}
