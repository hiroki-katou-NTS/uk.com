/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.jobtitle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitle;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CjtmtJobTitle;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CjtmtJobTitlePK_;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CjtmtJobTitle_;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMaster_;

/**
 * The Class JpaJobTitleRepository.
 */
@Stateless
public class JpaJobTitleRepository extends JpaRepository implements JobTitleRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleRepository#
	 * findAll(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<JobTitle> findAll(String companyId, GeneralDate referenceDate) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CjtmtJobTitle> cq = cb.createQuery(CjtmtJobTitle.class);
		Root<CjtmtJobTitle> root = cq.from(CjtmtJobTitle.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList
				.add(cb.equal(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.companyId), companyId));
		predicateList.add(cb.lessThanOrEqualTo(root.get(CjtmtJobTitle_.startDate), referenceDate));
		predicateList.add(cb.greaterThanOrEqualTo(root.get(CjtmtJobTitle_.endDate), referenceDate));

		List<Order> orderList = new ArrayList<Order>();
		// Sort by sequence master.
		orderList.add(cb.asc(root.get(CjtmtJobTitle_.csqmtSequenceMaster).get(CsqmtSequenceMaster_.order)));
		// Sort by job code.
		orderList.add(cb.asc(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.jobCode)));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(orderList);

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the cjtmt job title
	 */
	private CjtmtJobTitle toEntity(JobTitle domain) {
		CjtmtJobTitle entity = new CjtmtJobTitle();
		domain.saveToMemento(new JpaJobTitleSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the job title
	 */
	private JobTitle toDomain(CjtmtJobTitle entity) {
		return new JobTitle(new JpaJobTitleGetMemento(entity));
	}
}
