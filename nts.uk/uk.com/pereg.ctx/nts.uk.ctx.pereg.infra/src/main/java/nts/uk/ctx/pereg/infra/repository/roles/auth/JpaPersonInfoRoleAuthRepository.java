package nts.uk.ctx.pereg.infra.repository.roles.auth;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuth;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.infra.entity.roles.auth.category.PpemtPersonCategoryAuth;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaPersonInfoRoleAuthRepository extends JpaRepository implements PersonInfoRoleAuthRepository {

	private final String SEL_CATEGORY_BY_ROLEID = "SELECT c FROM PpemtPersonCategoryAuth c  WHERE c.ppemtPersonCategoryAuthPk.roleId =:roleId ";

	@Override
	public Optional<PersonInfoRoleAuth> get(String roleId, String companyId) {

		List<PersonInfoCategoryAuth> categoryAuthList = this.queryProxy()
				.query(SEL_CATEGORY_BY_ROLEID, PpemtPersonCategoryAuth.class).setParameter("roleId", roleId)
				.getList(c -> toDomain(c));

		if (categoryAuthList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new PersonInfoRoleAuth(roleId, companyId, categoryAuthList));
	}

	private PersonInfoCategoryAuth toDomain(PpemtPersonCategoryAuth entity) {
		return PersonInfoCategoryAuth.createFromJavaType(entity.ppemtPersonCategoryAuthPk.roleId,
				entity.ppemtPersonCategoryAuthPk.personInfoCategoryAuthId, entity.allowPersonRef, entity.allowOtherRef,
				entity.selfPastHisAuth, entity.selfFutureHisAuth, entity.selfAllowAddHis, entity.selfAllowDelHis,
				entity.otherPastHisAuth, entity.otherFutureHisAuth, entity.otherAllowAddHis, entity.otherAllowDelHis,
				entity.selfAllowAddMulti, entity.selfAllowDelMulti, entity.otherAllowAddMulti,
				entity.otherAllowDelMulti);
	}

}
