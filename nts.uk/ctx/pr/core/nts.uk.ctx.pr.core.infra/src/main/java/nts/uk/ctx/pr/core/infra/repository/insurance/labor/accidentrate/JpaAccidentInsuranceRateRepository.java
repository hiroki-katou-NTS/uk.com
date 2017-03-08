/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

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
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsuPK;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsuPK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu_;

/**
 * The Class JpaAccidentInsuranceRateRepository.
 */
@Stateless
public class JpaAccidentInsuranceRateRepository extends JpaRepository implements AccidentInsuranceRateRepository {

	/** The Constant BEGIN_FIRST. */
	public static final int BEGIN_FIRST = 0;

	/** The Constant BEGIN_SENDCOND. */
	public static final int BEGIN_SECOND = 1;

	/** The Constant SIZE_SECOND. */
	public static final int SIZE_SECOND = 2;

	/** The Constant SIZE_TEN. */
	public static final int SIZE_TEN = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#add(nts.uk.ctx.pr.core.dom.insurance.
	 * labor.accidentrate.AccidentInsuranceRate)
	 */
	@Override
	public void add(AccidentInsuranceRate rate) {
		this.commandProxy().insertAll(ramdomHistory(rate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#update(nts.uk.ctx.pr.core.dom.insurance.
	 * labor.accidentrate.AccidentInsuranceRate)
	 */
	@Override
	public void update(AccidentInsuranceRate rate) {
		this.commandProxy().updateAll(toEntity(rate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(CompanyCode companyCode, String historyId, Long version) {
		List<AccidentInsuranceRate> lstAccidentInsuranceRate = findAll(companyCode);
		if (lstAccidentInsuranceRate != null && !lstAccidentInsuranceRate.isEmpty()) {
			if (lstAccidentInsuranceRate.get(BEGIN_FIRST).getHistoryId().equals(historyId)) {
				List<QismtWorkAccidentInsu> lstRateRemove = this.findDataById(companyCode, historyId);
				this.commandProxy().removeAll(lstRateRemove);
				if (lstAccidentInsuranceRate.size() >= SIZE_SECOND) {
					List<QismtWorkAccidentInsu> lstRateUpdate = this.findDataById(companyCode,
							lstAccidentInsuranceRate.get(BEGIN_SECOND).getHistoryId());
					for (QismtWorkAccidentInsu qismtWorkAccidentInsu : lstRateUpdate) {
						qismtWorkAccidentInsu.setEndYm(YearMonth
								.of(DateTimeConstraints.LIMIT_YEAR.max(), DateTimeConstraints.LIMIT_MONTH.max()).v());
					}
					this.commandProxy().updateAll(lstRateUpdate);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findAll(int)
	 */
	@Override
	public List<AccidentInsuranceRate> findAll(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode.v()));
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<AccidentInsuranceRate> lstAccidentInsuranceRate = query.getResultList().stream()
				.map(item -> toDomainHistory(item)).collect(Collectors.toList());
		return lstAccidentInsuranceRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<AccidentInsuranceRate> findById(CompanyCode companyCode, String historyId) {
		return Optional.ofNullable(toDomain(findDataById(companyCode, historyId)));
	}

	/**
	 * Find data by id.
	 *
	 * @param companyCode
	 *            the company code
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> findDataById(CompanyCode companyCode, String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode.v()));
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.histId),
				historyId));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = query.getResultList();
		return lstQismtWorkAccidentInsu;
	}

	/**
	 * Find between.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> findBetween(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode.v()));
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		return query.getResultList();
	}

	/**
	 * To entity.
	 *
	 * @param rate
	 *            the rate
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> toEntity(AccidentInsuranceRate rate) {
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = new ArrayList<>();
		for (int i = BEGIN_FIRST; i < SIZE_TEN; i++) {
			lstQismtWorkAccidentInsu.add(new QismtWorkAccidentInsu());
		}
		rate.saveToMemento(new JpaAccidentInsuranceRateSetMemento(lstQismtWorkAccidentInsu));
		return lstQismtWorkAccidentInsu;
	}

	public AccidentInsuranceRate toDomain(List<QismtWorkAccidentInsu> entity) {
		return new AccidentInsuranceRate(new JpaAccidentInsuranceRateGetMemento(entity));
	}

	public AccidentInsuranceRate toDomainHistory(QismtWorkAccidentInsu entity) {
		return new AccidentInsuranceRate(new JpaHistoryAccidentInsuranceRateGetMemento(entity));
	}

	/**
	 * Ramdom history.
	 *
	 * @param rate
	 *            the rate
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> ramdomHistory(AccidentInsuranceRate rate) {
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = toEntity(rate);
		String historyId = IdentifierUtil.randomUniqueId();
		for (QismtWorkAccidentInsu qismtWorkAccidentInsu : lstQismtWorkAccidentInsu) {
			QismtWorkAccidentInsuPK pk = qismtWorkAccidentInsu.getQismtWorkAccidentInsuPK();
			pk.setHistId(historyId);
			qismtWorkAccidentInsu.setQismtWorkAccidentInsuPK(pk);
		}
		return lstQismtWorkAccidentInsu;
	}

	@Override
	public Optional<AccidentInsuranceRate> findFirstData(CompanyCode companyCode) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode.v()));
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = query.getResultList();
		if (ListUtil.isEmpty(lstQismtWorkAccidentInsu)) {
			return Optional.empty();
		}
		return findById(companyCode,
				lstQismtWorkAccidentInsu.get(BEGIN_FIRST).getQismtWorkAccidentInsuPK().getHistId());
	}

	@Override
	public void updateYearMonth(AccidentInsuranceRate rate, YearMonth yearMonth) {
		// to entity
		List<QismtWorkAccidentInsu> lstEntity = toEntity(rate);
		for (QismtWorkAccidentInsu entity : lstEntity) {
			entity.setEndYm(yearMonth.v());
		}
		update(toDomain(lstEntity));
	}

	@Override
	public Optional<AccidentInsuranceRate> findBetweenUpdate(CompanyCode companyCode, YearMonth yearMonth,
			String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode.v()));
		lstpredicate.add(criteriaBuilder.notEqual(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.histId),
				historyId));
		lstpredicate.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));
		lstpredicate.add(criteriaBuilder.le(root.get(QismtWorkAccidentInsu_.endYm), yearMonth.previousMonth().v()));
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = query.getResultList();
		if (ListUtil.isEmpty(lstQismtWorkAccidentInsu)) {
			return Optional.empty();
		}
		return findById(companyCode,
				lstQismtWorkAccidentInsu.get(BEGIN_FIRST).getQismtWorkAccidentInsuPK().getHistId());
	}

}
