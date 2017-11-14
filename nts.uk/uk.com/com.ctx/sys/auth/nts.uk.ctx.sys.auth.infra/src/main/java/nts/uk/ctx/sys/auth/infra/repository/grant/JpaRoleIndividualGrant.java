/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.grant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrant;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrantPK_;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrant_;

@Stateless
public class JpaRoleIndividualGrant extends JpaRepository implements RoleIndividualGrantRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository#findByUserAndRole(java.lang.String, nts.uk.ctx.sys.auth.dom.role.RoleType)
	 */
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository#findByUser(java.lang.String)
	 */
	@Override
	public Optional<RoleIndividualGrant> findByUser(String userId,GeneralDate date) {
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
		cq.where(predicateList.toArray(new Predicate[] {}));
		predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(SacmtRoleIndiviGrant_.strD), date));
		predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(SacmtRoleIndiviGrant_.endD), date));

		SacmtRoleIndiviGrant sacmtRoleIndiviGrant = em.createQuery(cq).getSingleResult();
		return Optional.of(new RoleIndividualGrant(new JpaRoleIndiviGrantGetMemento(sacmtRoleIndiviGrant)));
	}

	@Override
	public List<RoleIndividualGrant> findByRoleId(String roleId) {
		List<RoleIndividualGrant> result = new ArrayList<RoleIndividualGrant>();
		
		String query ="SELECT e FROM SacmtRoleIndiviGrant e WHERE e.roleId = :roleId";
		List<SacmtRoleIndiviGrant> entities = this.queryProxy().query(query, SacmtRoleIndiviGrant.class)
				.setParameter("roleId", roleId).getList();
		if (entities != null && !entities.isEmpty()) {
			return entities.stream().map(e ->new RoleIndividualGrant(new JpaRoleIndiviGrantGetMemento(e))).collect(Collectors.toList());
		}
		return result;
	}

}
