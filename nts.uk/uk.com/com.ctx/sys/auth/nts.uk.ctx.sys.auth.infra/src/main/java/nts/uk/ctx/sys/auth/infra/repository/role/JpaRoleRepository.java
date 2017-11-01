/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.infra.entity.role.SacmtRole;
import nts.uk.ctx.sys.auth.infra.entity.role.SacmtRole_;

/**
 * The Class JpaRoleRepository.
 */
@Stateless
public class JpaRoleRepository extends JpaRepository implements RoleRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.role.RoleRepository#findById(java.lang.String)
	 */
	@Override
	public List<Role> findById(String roleId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<SacmtRole> cq = criteriaBuilder.createQuery(SacmtRole.class);
		Root<SacmtRole> root = cq.from(SacmtRole.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(criteriaBuilder.equal(root.get(SacmtRole_.id), roleId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<SacmtRole> sacmtRoles = em.createQuery(cq).getResultList();
		return sacmtRoles.stream().map(sacmtRole -> {
			return new Role(new JpaRoleGetMemento(sacmtRole));
		}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.role.RoleRepository#findByListRoleId(java.lang.String, java.util.List)
	 */
	@Override
	public List<Role> findByListRoleId(String companyId, List<String> lstRoleId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<SacmtRole> cq = criteriaBuilder.createQuery(SacmtRole.class);
		Root<SacmtRole> root = cq.from(SacmtRole.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(root.get(SacmtRole_.id).in(lstRoleId));
		predicateList.add(criteriaBuilder.equal(root.get(SacmtRole_.cid), companyId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<SacmtRole> sacmtRoles = em.createQuery(cq).getResultList();
		return sacmtRoles.stream().map(sacmtRole -> {
			return new Role(new JpaRoleGetMemento(sacmtRole));
		}).collect(Collectors.toList());
	}

}
