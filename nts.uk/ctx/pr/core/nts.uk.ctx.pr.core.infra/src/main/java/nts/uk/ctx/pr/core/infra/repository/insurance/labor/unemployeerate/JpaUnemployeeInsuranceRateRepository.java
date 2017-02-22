/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.infra.repository.insurance.labor.unemployeerate;

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
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRatePK;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRatePK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRate_;

/**
 * The Class JpaUnemployeeInsuranceRateRepository.
 */
@Stateless
public class JpaUnemployeeInsuranceRateRepository extends JpaRepository implements UnemployeeInsuranceRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#add(nts.uk.ctx.pr.core.dom.insurance.
	 * labor.unemployeerate.UnemployeeInsuranceRate)
	 */
	@Override
	public void add(UnemployeeInsuranceRate rate) {
		String historyId = IdentifierUtil.randomUniqueId();
		QismtEmpInsuRate entity = toEntity(rate);
		QismtEmpInsuRatePK pk = entity.getQismtEmpInsuRatePK();
		pk.setHistId(historyId);
		entity.setQismtEmpInsuRatePK(pk);
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#update(nts.uk.ctx.pr.core.dom.insurance
	 * .labor.unemployeerate.UnemployeeInsuranceRate)
	 */
	@Override
	public void update(UnemployeeInsuranceRate rate) {
		this.commandProxy().update(toEntity(rate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#remove(java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#findById(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<UnemployeeInsuranceRate> findById(CompanyCode companyCode, String historyId) {
		return this.queryProxy().find(new QismtEmpInsuRatePK(companyCode.v(), historyId), QismtEmpInsuRate.class)
				.map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#isInvalidDateRange(nts.uk.ctx.pr.core.
	 * dom.insurance.MonthRange)
	 */
	@Override
	public boolean isInvalidDateRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#findAll(nts.uk.ctx.core.dom.company.
	 * CompanyCode)
	 */
	@Override
	public List<UnemployeeInsuranceRate> findAll(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtEmpInsuRate> cq = criteriaBuilder.createQuery(QismtEmpInsuRate.class);
		Root<QismtEmpInsuRate> root = cq.from(QismtEmpInsuRate.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder
				.equal(root.get(QismtEmpInsuRate_.qismtEmpInsuRatePK).get(QismtEmpInsuRatePK_.ccd), companyCode.v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtEmpInsuRate> query = em.createQuery(cq);
		List<UnemployeeInsuranceRate> lstUnemployeeInsuranceRate = query.getResultList().stream()
				.map(item -> toDomain(item)).collect(Collectors.toList());
		return lstUnemployeeInsuranceRate;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the unemployee insurance rate
	 */
	private UnemployeeInsuranceRate toDomain(QismtEmpInsuRate entity) {
		UnemployeeInsuranceRate domain = new UnemployeeInsuranceRate(new JpaUnemployeeInsuranceRateGetMemento(entity));
		return domain;

	}

	/**
	 * To entity.
	 *
	 * @param UnemployeeInsuranceRate
	 *            the unemployee insurance rate
	 * @return the qismt emp insu rate
	 */
	private QismtEmpInsuRate toEntity(UnemployeeInsuranceRate UnemployeeInsuranceRate) {
		QismtEmpInsuRate entity = new QismtEmpInsuRate();
		UnemployeeInsuranceRate.saveToMemento(new JpaUnemployeeInsuranceRateSetMemento(entity));
		return entity;
	}
}
