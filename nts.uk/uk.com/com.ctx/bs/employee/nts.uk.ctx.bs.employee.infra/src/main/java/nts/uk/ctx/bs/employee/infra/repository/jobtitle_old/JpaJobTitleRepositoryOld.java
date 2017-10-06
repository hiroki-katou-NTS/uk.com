/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle_old;

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
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitleRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CjtmtJobTitle;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CjtmtJobTitlePK_;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CjtmtJobTitle_;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CsqmtSequenceMaster_;

/**
 * The Class JpaJobTitleRepository.
 */
@Stateless
public class JpaJobTitleRepositoryOld extends JpaRepository implements JobTitleRepository {

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
		predicateList.add(
				cb.equal(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.companyId),
						companyId));
		predicateList.add(cb.lessThanOrEqualTo(root.get(CjtmtJobTitle_.startDate), referenceDate));
		predicateList.add(cb.greaterThanOrEqualTo(root.get(CjtmtJobTitle_.endDate), referenceDate));

		List<Order> orderList = new ArrayList<Order>();
		// Sort by sequence master.
		orderList.add(cb
				.asc(root.get(CjtmtJobTitle_.csqmtSequenceMaster).get(CsqmtSequenceMaster_.order)));
		// Sort by job code.
		orderList.add(
				cb.asc(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.jobCode)));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(orderList);

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleRepository#
	 * findByJobIds(java.util.List)
	 */
	@Override
	public List<JobTitle> findByJobIds(List<String> jobIds) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CjtmtJobTitle> cq = cb.createQuery(CjtmtJobTitle.class);
		Root<CjtmtJobTitle> root = cq.from(CjtmtJobTitle.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.jobId).in(jobIds));

		List<Order> orderList = new ArrayList<Order>();
		// Sort by sequence master.
		orderList.add(cb
				.asc(root.get(CjtmtJobTitle_.csqmtSequenceMaster).get(CsqmtSequenceMaster_.order)));
		// Sort by job code.
		orderList.add(
				cb.asc(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.jobCode)));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(orderList);

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleRepository#findByJobIds(java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<JobTitle> findByJobIds(String companyId, List<String> jobIds,
			GeneralDate baseDate) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CjtmtJobTitle> cq = cb.createQuery(CjtmtJobTitle.class);
		Root<CjtmtJobTitle> root = cq.from(CjtmtJobTitle.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.companyId),
						companyId));

		predicateList.add(
				root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.jobId).in(jobIds));

		predicateList.add(cb.lessThanOrEqualTo(root.get(CjtmtJobTitle_.startDate), baseDate));

		predicateList.add(cb.greaterThanOrEqualTo(root.get(CjtmtJobTitle_.endDate), baseDate));

		List<Order> orderList = new ArrayList<Order>();
		// Sort by sequence master.
		orderList.add(cb
				.asc(root.get(CjtmtJobTitle_.csqmtSequenceMaster).get(CsqmtSequenceMaster_.order)));
		// Sort by job code.
		orderList.add(
				cb.asc(root.get(CjtmtJobTitle_.cjtmtJobTitlePK).get(CjtmtJobTitlePK_.jobCode)));

		cq.where(predicateList.toArray(new Predicate[] {}));
		
		cq.orderBy(orderList);

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the job title
	 */
	private JobTitle toDomain(CjtmtJobTitle entity) {
		return new JobTitle(new JpaJobTitleGetMemento(entity));
	}
}
