/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import java.math.BigDecimal;
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
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearnPK;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearnPK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRatePK;

/**
 * The Class JpaHealthInsuranceAvgearnRepository.
 */
@Stateless
public class JpaHealthInsuranceAvgearnRepository extends JpaRepository implements HealthInsuranceAvgearnRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#update(nts.uk.ctx.pr.core.dom.insurance.
	 * social.healthavgearn.HealthInsuranceAvgearn)
	 */
	@Override
	public void update(List<HealthInsuranceAvgearn> healthInsuranceAvgearns, String ccd, String officeCd) {
		// Find healthInsuRate
		QismtHealthInsuRate healthInsuRate = this.queryProxy()
				.find(new QismtHealthInsuRatePK(ccd, officeCd, healthInsuranceAvgearns.get(0).getHistoryId()),
						QismtHealthInsuRate.class)
				.get();

		// Set Updated HealthInsuAvgearnList
		healthInsuRate.setQismtHealthInsuAvgearnList(healthInsuranceAvgearns.stream()
				.map(domain -> toEntity(domain, ccd, officeCd)).collect(Collectors.toList()));

		// Update healthInsuRate
		this.commandProxy().update(healthInsuRate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String ccd, String officeCd, String histId, Integer levelCode) {
		this.commandProxy().remove(QismtHealthInsuAvgearnPK.class,
				new QismtHealthInsuAvgearnPK(ccd, officeCd, histId, BigDecimal.valueOf(levelCode)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#findById(java.lang.String)
	 */
	@Override
	public List<HealthInsuranceAvgearn> findById(String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuAvgearn> cq = criteriaBuilder.createQuery(QismtHealthInsuAvgearn.class);
		Root<QismtHealthInsuAvgearn> root = cq.from(QismtHealthInsuAvgearn.class);
		cq.select(root);
		List<Predicate> listpredicate = new ArrayList<>();
		listpredicate.add(criteriaBuilder.equal(
				root.get(QismtHealthInsuAvgearn_.qismtHealthInsuAvgearnPK).get(QismtHealthInsuAvgearnPK_.histId),
				historyId));
		cq.where(listpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtHealthInsuAvgearn> query = em.createQuery(cq);
		List<HealthInsuranceAvgearn> listHealthInsuranceAvgearn = query.getResultList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList());
		return listHealthInsuranceAvgearn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#find(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Optional<HealthInsuranceAvgearn> find(String companyCode, String officeCode, String historyId,
			Integer levelCode) {
		return this.queryProxy()
				.find(new QismtHealthInsuAvgearnPK(companyCode, officeCode, historyId, BigDecimal.valueOf(levelCode)),
						QismtHealthInsuAvgearn.class)
				.map(c -> toDomain(c));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the health insurance avgearn
	 */
	private static HealthInsuranceAvgearn toDomain(QismtHealthInsuAvgearn entity) {
		HealthInsuranceAvgearn domain = new HealthInsuranceAvgearn(new JpaHealthInsuranceAvgearnGetMemento(entity));

		return domain;
	}

	/**
	 * To entity.
	 *
	 * @param healthInsuranceAvgearn the health insurance avgearn
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 * @return the qismt health insu avgearn
	 */
	private QismtHealthInsuAvgearn toEntity(HealthInsuranceAvgearn healthInsuranceAvgearn, String ccd,
			String officeCd) {
		QismtHealthInsuAvgearn entity = new QismtHealthInsuAvgearn();
		healthInsuranceAvgearn.saveToMemento(new JpaHealthInsuranceAvgearnSetMemento(entity));

		// 2 field nay bi thua?
		entity.setHealthInsuAvgEarn(BigDecimal.valueOf(5));
		entity.setHealthInsuUpperLimit(BigDecimal.valueOf(3));

		// Set company code & office code
		QismtHealthInsuAvgearnPK pk = entity.getQismtHealthInsuAvgearnPK();
		pk.setCcd(ccd);
		pk.setSiOfficeCd(officeCd);

		return entity;
	}

}
