package repository.roles;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.roles.auth.category.PpemtPersonCategoryAuth;
import entity.roles.auth.category.PpemtPersonCategoryAuthPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;

@Stateless
public class JpaPersonInfoCategoryAuthRepository extends JpaRepository implements PersonInfoCategoryAuthRepository {

	private final String SEL_NO_WHERE = "SELECT c FROM PpemtPersonCategoryAuth c";

	private final String SEL_1 = SEL_NO_WHERE + " WHERE c.ppemtPersonCategoryAuthPk.roleId =:roleId ";
	private final String SEL_2 = SEL_1
			+ " AND  c.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId =:personInfoCategoryAuthId ";

	private static PersonInfoCategoryAuth toDomain(PpemtPersonCategoryAuth entity) {
		val domain = PersonInfoCategoryAuth.createFromJavaType(entity.ppemtPersonCategoryAuthPk.roleId,
				entity.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId, entity.allowPersonRef, entity.allowOtherRef,
				entity.allowOtherCompanyRef, entity.selfPastHisAuth, entity.selfFutureHisAuth, entity.selfAllowAddHis,
				entity.selfAllowDelHis, entity.otherPastHisAuth, entity.otherFutureHisAuth, entity.otherAllowAddHis,
				entity.otherAllowDelHis, entity.selfAllowAddMulti, entity.selfAllowDelMulti, entity.otherAllowAddMulti,
				entity.otherAllowDelMulti);
		return domain;
	}

	private static PpemtPersonCategoryAuth toEntity(PersonInfoCategoryAuth domain) {
		PpemtPersonCategoryAuth entity = new PpemtPersonCategoryAuth();
		entity.ppemtPersonCategoryAuthPk = new PpemtPersonCategoryAuthPk(domain.getRoleId(),
				domain.getPersonInfoCategoryAuthId());
		entity.allowOtherCompanyRef = domain.getAllowOtherCompanyRef().value;
		entity.allowOtherRef = domain.getAllowOtherRef().value;
		entity.allowPersonRef = domain.getAllowPersonRef().value;
		entity.otherAllowAddHis = domain.getOtherAllowAddHis().value;
		entity.otherAllowAddHis = domain.getOtherAllowAddHis().value;
		entity.otherFutureHisAuth = domain.getOtherFutureHisAuth().value;
		entity.otherPastHisAuth = domain.getOtherPastHisAuth().value;
		entity.selfAllowAddHis = domain.getSelfAllowAddHis().value;
		entity.selfAllowDelHis = domain.getSelfAllowAddHis().value;
		entity.selfFutureHisAuth = domain.getSelfFutureHisAuth().value;
		entity.selfPastHisAuth = domain.getSelfPastHisAuth().value;
		entity.selfAllowAddMulti = domain.getSelfAllowAddMulti().value;
		entity.selfAllowDelMulti = domain.getSelfAllowDelHis().value;
		entity.otherAllowAddMulti = domain.getOtherAllowAddMulti().value;
		entity.otherAllowDelMulti = domain.getOtherAllowDelMulti().value;
		return entity;

	}

	@Override
	public List<PersonInfoCategoryAuth> getAllPersonCategoryAuth() {
		return this.queryProxy().query(SEL_NO_WHERE, PpemtPersonCategoryAuth.class).getList(c -> toDomain(c));
	}

	@Override
	public List<PersonInfoCategoryAuth> getAllPersonCategoryAuthByRoleId(String roleId) {
		return this.queryProxy().query(SEL_1, PpemtPersonCategoryAuth.class).setParameter("roleId", roleId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuth(String roleId, String personCategoryAuthId) {
		return this.queryProxy().query(SEL_2, PpemtPersonCategoryAuth.class).setParameter("roleId", roleId)
				.setParameter("personInfoCategoryAuthId", personCategoryAuthId).getSingle().map(e -> {
					return Optional.of(toDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public void add(PersonInfoCategoryAuth domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PersonInfoCategoryAuth domain) {
		this.commandProxy().update(toEntity(domain));

	}

	@Override
	public void delete(String roleId, String personCategoryAuthId) {
		this.commandProxy().remove(PpemtPersonCategoryAuth.class,
				new PpemtPersonCategoryAuthPk(roleId, personCategoryAuthId));
	}
}
