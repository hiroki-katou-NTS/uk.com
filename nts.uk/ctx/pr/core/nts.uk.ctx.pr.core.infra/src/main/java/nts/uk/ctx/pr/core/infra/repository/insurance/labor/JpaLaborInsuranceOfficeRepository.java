/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor;

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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOffice;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOfficePK;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOfficePK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOffice_;

/**
 * The Class JpaLaborInsuranceOfficeRepository.
 */
@Stateless
public class JpaLaborInsuranceOfficeRepository extends JpaRepository implements LaborInsuranceOfficeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#add
	 * (nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void add(LaborInsuranceOffice office) {
		this.commandProxy().insert(toEntity(office));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * update(nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void update(LaborInsuranceOffice office) {
		this.commandProxy().update(toEntity(office));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * findAll(java.lang.String)
	 */
	@Override
	public List<LaborInsuranceOffice> findAll(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtLaborInsuOffice> cq = criteriaBuilder.createQuery(QismtLaborInsuOffice.class);
		Root<QismtLaborInsuOffice> root = cq.from(QismtLaborInsuOffice.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtLaborInsuOffice_.qismtLaborInsuOfficePK).get(QismtLaborInsuOfficePK_.ccd),
				companyCode.v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtLaborInsuOffice> query = em.createQuery(cq);
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = query.getResultList().stream().map(item -> toDomain(item))
				.collect(Collectors.toList());
		return lstLaborInsuranceOffice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<LaborInsuranceOffice> findById(CompanyCode companyCode, OfficeCode officeCode) {
		return this.queryProxy()
				.find(new QismtLaborInsuOfficePK(companyCode.v(), officeCode.v()), QismtLaborInsuOffice.class)
				.map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * isDuplicateCode(nts.uk.ctx.core.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.core.dom.insurance.OfficeCode)
	 */
	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, OfficeCode code) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		cq.select(criteriaBuilder.count(cq.from(QismtLaborInsuOffice.class)));
		Root<QismtLaborInsuOffice> root = cq.from(QismtLaborInsuOffice.class);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtLaborInsuOffice_.qismtLaborInsuOfficePK).get(QismtLaborInsuOfficePK_.ccd),
				companyCode.v()));
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtLaborInsuOffice_.qismtLaborInsuOfficePK).get(QismtLaborInsuOfficePK_.liOfficeCd),
				code.v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		return (em.createQuery(cq).getSingleResult() > 0);
	}

	/**
	 * To entity.
	 *
	 * @param laborInsuranceOffice
	 *            the labor insurance office
	 * @return the qismt labor insu office
	 */
	private QismtLaborInsuOffice toEntity(LaborInsuranceOffice laborInsuranceOffice) {
		QismtLaborInsuOffice entity = new QismtLaborInsuOffice();
		laborInsuranceOffice.saveToMemento(new JpaLaborInsuranceOfficeSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the labor insurance office
	 */
	private static LaborInsuranceOffice toDomain(QismtLaborInsuOffice entity) {
		LaborInsuranceOffice domain = new LaborInsuranceOffice(new JpaLaborInsuranceOfficeGetMemento(entity));
		return domain;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * remove(nts.uk.ctx.core.dom.company.CompanyCode, java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	public void remove(CompanyCode companyCode, String officeCode, Long version) {
		this.commandProxy().remove(QismtLaborInsuOffice.class, new QismtLaborInsuOfficePK(companyCode.v(), officeCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * addList(java.util.List)
	 */
	@Override
	public void addList(List<LaborInsuranceOffice> lstOffice) {
		this.commandProxy().insertAll(lstOffice.stream().map(c -> toEntity(c)).collect(Collectors.toList()));
	}
}
