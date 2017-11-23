/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.role;

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
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.infra.entity.role.SaumtRole;
import nts.uk.ctx.sys.auth.infra.entity.role.SacmtRole_;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SaumtDefaultRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SaumtDefaultRoleSetPK;

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

		CriteriaQuery<SaumtRole> cq = criteriaBuilder.createQuery(SaumtRole.class);
		Root<SaumtRole> root = cq.from(SaumtRole.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(criteriaBuilder.equal(root.get(SacmtRole_.id), roleId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<SaumtRole> sacmtRoles = em.createQuery(cq).getResultList();
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

		CriteriaQuery<SaumtRole> cq = criteriaBuilder.createQuery(SaumtRole.class);
		Root<SaumtRole> root = cq.from(SaumtRole.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(root.get(SacmtRole_.id).in(lstRoleId));
		predicateList.add(criteriaBuilder.equal(root.get(SacmtRole_.cid), companyId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<SaumtRole> sacmtRoles = em.createQuery(cq).getResultList();
		return sacmtRoles.stream().map(sacmtRole -> {
			return new Role(new JpaRoleGetMemento(sacmtRole));
		}).collect(Collectors.toList());
	}
	
	
	
	@Override
	public void insert(Role role) {
		this.commandProxy().insert(toEntity(role));		
	}

	@Override
	public void update(Role role) {
		this.commandProxy().update(toEntity(role));		
	}
	@Override
	public void remove(String roleId) {		
		this.commandProxy().remove(SaumtRole.class, roleId);
	}
	
	private SaumtRole  toEntity(Role role){
		SaumtRole entity = new SaumtRole();
		entity.setId(role.getRoleId());
		entity.setCid(role.getCompanyId());
		entity.setCode(role.getRoleCode().toString());
		entity.setRoleType(role.getRoleType().value);
		entity.setReferenceRange(role.getEmployeeReferenceRange().value);
		entity.setName(role.getName().toString());
		entity.setContractCode(role.getContractCode().toString());
		entity.setAssignAtr(role.getAssignAtr().value);
		return entity;
	}

	@Override
	public List<Role> findByType(String companyId, int roleType) {
		List<Role> result = new ArrayList<>();
		
		String query ="SELECT e FROM SaumtRole e WHERE e.cid = :companyId AND e.roleType = :roleType ORDER BY e.assignAtr ASC, e.code ASC ";
		List<SaumtRole> entities = this.queryProxy().query(query, SaumtRole.class)
				.setParameter("companyId", companyId).setParameter("roleType", roleType).getList();
		if (entities != null && entities.size() !=0) {
			return entities.stream().map(x->new Role(new JpaRoleGetMemento(x))).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public List<Role> findByType(int roleType) {
		List<Role> result = new ArrayList<>();
		String query ="SELECT e FROM SaumtRole e WHERE e.roleType = :roleType ORDER BY e.assignAtr ASC, e.code ASC ";
		List<SaumtRole> entities = this.queryProxy().query(query, SaumtRole.class).setParameter("roleType", roleType).getList();
		if (entities != null  && !entities.isEmpty()) {
			return entities.stream().map(x ->new Role(new JpaRoleGetMemento(x))).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public Optional<Role> findByRoleId(String roleId) {
		String query ="SELECT e FROM SaumtRole e WHERE e.Id = :roleId ";
		return this.queryProxy().query(query, SaumtRole.class)
				.setParameter("roleId", roleId).getList().stream().map(x ->new Role(new JpaRoleGetMemento(x))).findFirst();
	}

}
