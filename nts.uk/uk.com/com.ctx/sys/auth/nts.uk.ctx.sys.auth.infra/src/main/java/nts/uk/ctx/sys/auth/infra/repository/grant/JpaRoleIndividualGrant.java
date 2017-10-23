/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.grant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrant;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrantPK_;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrant_;

@Stateless
public class JpaRoleIndividualGrant extends JpaRepository implements RoleIndividualGrantRepository {

	@Override
	public Optional<RoleIndividualGrant> findByUserAndRole(String userId, RoleType roleType) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<SacmtRoleIndiviGrant> cq = criteriaBuilder.createQuery(SacmtRoleIndiviGrant.class);
		Root<SacmtRoleIndiviGrant> root = cq.from(SacmtRoleIndiviGrant.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(criteriaBuilder.equal(
				root.get(SacmtRoleIndiviGrant_.sacmtRoleIndiviGrantPK).get(SacmtRoleIndiviGrantPK_.userId), userId));
		predicateList.add(criteriaBuilder.equal(
				root.get(SacmtRoleIndiviGrant_.sacmtRoleIndiviGrantPK).get(SacmtRoleIndiviGrantPK_.roleType),
				roleType));
		cq.where(predicateList.toArray(new Predicate[] {}));

		SacmtRoleIndiviGrant sacmtRoleIndiviGrant = em.createQuery(cq).getSingleResult();
		return Optional.of(new RoleIndividualGrant(new JpaRoleIndiviGrantGetMemento(sacmtRoleIndiviGrant)));
	}

}
