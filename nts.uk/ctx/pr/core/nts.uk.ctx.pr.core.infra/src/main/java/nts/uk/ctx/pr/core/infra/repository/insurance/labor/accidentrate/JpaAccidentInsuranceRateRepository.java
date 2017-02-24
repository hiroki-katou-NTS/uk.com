/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsuPK;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsuPK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRatePK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyG;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyGPK;

/**
 * The Class JpaAccidentInsuranceRateRepository.
 */
@Stateless
public class JpaAccidentInsuranceRateRepository extends JpaRepository implements AccidentInsuranceRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#add(nts.uk.ctx.pr.core.dom.insurance.
	 * labor.accidentrate.AccidentInsuranceRate)
	 */
	@Override
	public void add(AccidentInsuranceRate rate) {
		if (isInvalidDateRange(rate.getCompanyCode(), rate.getApplyRange().getStartMonth())) {
			this.commandProxy().insertAll(ramdomHistory(rate));
		}

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
		if (isInvalidDateRangeUpdate(rate.getCompanyCode(), rate.getApplyRange().getStartMonth(),
				rate.getHistoryId())) {
			this.commandProxy().updateAll(toEntity(rate));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

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

	/**
	 * From domain.
	 *
	 * @param historyId
	 *            the history id
	 * @param companyCode
	 *            the company code
	 * @param monthRange
	 *            the month range
	 * @return the unemployee insurance rate
	 */
	public AccidentInsuranceRate fromDomain(String historyId, CompanyCode companyCode, MonthRange monthRange) {
		return new AccidentInsuranceRate(new AccidentInsuranceRateGetMemento() {

			@Override
			public Set<InsuBizRateItem> getRateItems() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getHistoryId() {
				// TODO Auto-generated method stub
				return historyId;
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return companyCode;
			}

			@Override
			public MonthRange getApplyRange() {
				// TODO Auto-generated method stub
				return monthRange;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findById(java.lang.String)
	 */
	@Override
	public AccidentInsuranceRate findById(CompanyCode companyCode, String historyId) {
		return toDomain(findDataById(companyCode, historyId));
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
	 * @param yearMonth
	 *            the year month
	 * @return the list
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findBetween(nts.uk.ctx.core.dom.company.
	 * CompanyCode, java.lang.String)
	 */
	public List<QismtWorkAccidentInsu> findBetween(CompanyCode companyCode, YearMonth yearMonth) {
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
		lstpredicate.add(criteriaBuilder.le(root.get(QismtWorkAccidentInsu_.strYm), yearMonth.nextMonth().v()));
		lstpredicate.add(criteriaBuilder.ge(root.get(QismtWorkAccidentInsu_.endYm), yearMonth.previousMonth().v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		return query.getResultList();
	}

	/**
	 * Find between update.
	 *
	 * @param companyCode
	 *            the company code
	 * @param yearMonth
	 *            the year month
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> findBetweenUpdate(CompanyCode companyCode, YearMonth yearMonth,
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
		lstpredicate.add(criteriaBuilder.le(root.get(QismtWorkAccidentInsu_.strYm), yearMonth.nextMonth().v()));
		lstpredicate.add(criteriaBuilder.ge(root.get(QismtWorkAccidentInsu_.endYm), yearMonth.previousMonth().v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		return query.getResultList();
	}

	/**
	 * Find between end update.
	 *
	 * @param companyCode
	 *            the company code
	 * @param yearMonth
	 *            the year month
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> findBetweenEndUpdate(CompanyCode companyCode, YearMonth yearMonth,
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
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#isInvalidDateRange(nts.uk.ctx.core.dom.
	 * company.CompanyCode, nts.arc.time.YearMonth)
	 */
	@Override
	public boolean isInvalidDateRange(CompanyCode companyCode, YearMonth startMonth) {
		List<AccidentInsuranceRate> lstFindAllAccidentInsuranceRate = findAll(companyCode);
		if (lstFindAllAccidentInsuranceRate == null || lstFindAllAccidentInsuranceRate.isEmpty()) {
			return true;
		}
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = findBetween(companyCode, startMonth);
		if (lstQismtWorkAccidentInsu != null && lstQismtWorkAccidentInsu.size() == 1) {
			List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsuUpdate = findDataById(companyCode,
					lstQismtWorkAccidentInsu.get(0).getQismtWorkAccidentInsuPK().getHistId());
			for (QismtWorkAccidentInsu qismtWorkAccidentInsu : lstQismtWorkAccidentInsuUpdate) {
				qismtWorkAccidentInsu.setEndYm(startMonth.previousMonth().v());
			}
			update(toDomain(lstQismtWorkAccidentInsuUpdate));
			return true;
		}
		return false;
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
		for (int i = 0; i < 10; i++) {
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

	public boolean isInvalidDateRangeUpdate(CompanyCode companyCode, YearMonth startMonth, String historyId) {
		// data is begin update
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsuUpdate = findDataById(companyCode, historyId);
		if (lstQismtWorkAccidentInsuUpdate != null && !lstQismtWorkAccidentInsuUpdate.isEmpty()) {
			if (lstQismtWorkAccidentInsuUpdate.get(0).getStrYm() > startMonth.v()) {
				// begin update
				List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = findBetweenUpdate(companyCode, startMonth,
						historyId);
				if (lstQismtWorkAccidentInsu == null || lstQismtWorkAccidentInsu.isEmpty()) {
					return true;
				}
				if (lstQismtWorkAccidentInsu != null && lstQismtWorkAccidentInsu.size() == 1) {
					List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsuBeginUpdate = findDataById(companyCode,
							lstQismtWorkAccidentInsu.get(0).getQismtWorkAccidentInsuPK().getHistId());
					for (QismtWorkAccidentInsu qismtWorkAccidentInsu : lstQismtWorkAccidentInsuBeginUpdate) {
						qismtWorkAccidentInsu.setEndYm(startMonth.previousMonth().v());
					}
					update(toDomain(lstQismtWorkAccidentInsuBeginUpdate));
					return true;
				}
			} else if (lstQismtWorkAccidentInsuUpdate.get(0).getStrYm() < startMonth.v()) {
				// end update
				List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = findBetweenEndUpdate(companyCode, startMonth,
						historyId);
				if (lstQismtWorkAccidentInsu == null || lstQismtWorkAccidentInsu.isEmpty()) {
					return true;
				}
				if (lstQismtWorkAccidentInsu != null && lstQismtWorkAccidentInsu.size() == 1) {
					List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsuEndUpdate = findDataById(companyCode,
							lstQismtWorkAccidentInsu.get(0).getQismtWorkAccidentInsuPK().getHistId());
					for (QismtWorkAccidentInsu qismtWorkAccidentInsu : lstQismtWorkAccidentInsuEndUpdate) {
						qismtWorkAccidentInsu.setEndYm(startMonth.previousMonth().v());
					}
					update(toDomain(lstQismtWorkAccidentInsuEndUpdate));
					return true;
				}
			}
			return true;
		}
		return false;
	}

}
