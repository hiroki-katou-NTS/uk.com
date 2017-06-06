/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.company.organization.employment.Employment;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.CemptEmployment;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.CemptEmploymentPK;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.CemptEmploymentPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.CemptEmployment_;

/**
 * The Class JpaEmploymentRepository.
 */
@Stateless
public class JpaEmploymentRepository extends JpaRepository implements EmploymentRepository {

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository
	 * #findAll(java.lang.String)
	 */
	@Override
	public List<Employment> findAll(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<CemptEmployment> cq = bd.createQuery(CemptEmployment.class);

		// Root
		Root<CemptEmployment> root = cq.from(CemptEmployment.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(
				bd.equal(root.get(CemptEmployment_.cemptEmploymentPK).get(CemptEmploymentPK_.cid),
						companyId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<CemptEmployment> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository
	 * #findEmployment(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<Employment> findEmployment(String companyId, String employmentCode) {
		return this.queryProxy()
				.find(new CemptEmploymentPK(companyId, employmentCode), CemptEmployment.class)
				.map(e -> this.toDomain(e));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employment
	 */
	private Employment toDomain(CemptEmployment entity) {
		return new Employment(new JpaEmploymentGetMemento(entity));
	}
}
