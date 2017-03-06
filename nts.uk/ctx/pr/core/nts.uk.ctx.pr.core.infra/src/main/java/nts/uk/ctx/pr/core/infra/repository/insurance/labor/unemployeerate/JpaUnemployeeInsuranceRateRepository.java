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
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
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

	/** The Constant BEGIN_FIRST. */
	public static final int BEGIN_FIRST = 0;

	/** The Constant BEGIN_SENDCOND. */
	public static final int BEGIN_SECOND = 1;

	/** The Constant SIZE_ONE. */
	public static final int SIZE_ONE = 1;

	/** The Constant SIZE_TWO. */
	public static final int SIZE_TWO = 2;

	public static final YearMonth YEAR_MONTH_MAX = YearMonth.of(DateTimeConstraints.LIMIT_YEAR.max(),
			DateTimeConstraints.LIMIT_MONTH.max());

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#add(nts.uk.ctx.pr.core.dom.insurance.
	 * labor.unemployeerate.UnemployeeInsuranceRate)
	 */
	@Override
	public void add(UnemployeeInsuranceRate rate) {
		if (!isInvalidDateRange(rate.getCompanyCode(), rate.getApplyRange())) {
			String historyId = IdentifierUtil.randomUniqueId();
			QismtEmpInsuRate entity = toEntity(rate);
			QismtEmpInsuRatePK pk = entity.getQismtEmpInsuRatePK();
			pk.setHistId(historyId);
			entity.setQismtEmpInsuRatePK(pk);
			this.commandProxy().insert(entity);
		}
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
		if (!isInvalidDateRangeUpdate(rate.getCompanyCode(), rate.getApplyRange(), rate.getHistoryId())) {
			this.commandProxy().update(toEntity(rate));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateRepository#remove(java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	public void remove(CompanyCode companyCode, String historyId, Long version) {
		List<UnemployeeInsuranceRate> lstUnemployeeInsuranceRate = findAll(companyCode);
		if (!ListUtil.isEmpty(lstUnemployeeInsuranceRate)) {
			if (lstUnemployeeInsuranceRate.get(BEGIN_FIRST).getHistoryId().equals(historyId)) {
				// Start Begin
				this.commandProxy().remove(QismtEmpInsuRate.class, new QismtEmpInsuRatePK(companyCode.v(), historyId));
				if (lstUnemployeeInsuranceRate.size() >= SIZE_TWO) {
					UnemployeeInsuranceRate rateUpdate = lstUnemployeeInsuranceRate.get(1);
					QismtEmpInsuRate entityUpdate = toEntity(rateUpdate);
					// set max date
					entityUpdate.setEndYm(YEAR_MONTH_MAX.v());
					this.commandProxy().update(entityUpdate);
				}
			}
		}
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
	public boolean isInvalidDateRange(CompanyCode companyCode, MonthRange monthRange) {
		if (monthRange.getStartMonth().v() > monthRange.getEndMonth().v()) {
			return true;
		}
		List<UnemployeeInsuranceRate> lstUnemployeeInsuranceRate = findAll(companyCode);
		if (ListUtil.isEmpty(lstUnemployeeInsuranceRate)) {
			return false;
		}
		if (lstUnemployeeInsuranceRate.get(0).getApplyRange().getStartMonth().nextMonth().v() > monthRange
				.getStartMonth().v()) {
			return true;
		}
		List<QismtEmpInsuRate> lstQismtEmpInsuRate = findBetween(companyCode, monthRange.getStartMonth());
		if (lstQismtEmpInsuRate != null && lstQismtEmpInsuRate.size() == SIZE_ONE) {
			QismtEmpInsuRate qismtEmpInsuRateUpdate = findDataById(companyCode,
					lstQismtEmpInsuRate.get(BEGIN_FIRST).getQismtEmpInsuRatePK().getHistId());
			qismtEmpInsuRateUpdate.setEndYm(monthRange.getStartMonth().previousMonth().v());
			if (qismtEmpInsuRateUpdate != null)
				update(toDomain(qismtEmpInsuRateUpdate));
			return false;
		}
		return true;
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
		cq.orderBy(criteriaBuilder.desc(root.get(QismtEmpInsuRate_.strYm)));
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

	/**
	 * Find between.
	 *
	 * @param companyCode
	 *            the company code
	 * @param yearMonth
	 *            the year month
	 * @return the list
	 */
	public List<QismtEmpInsuRate> findBetween(CompanyCode companyCode, YearMonth yearMonth) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtEmpInsuRate> cq = criteriaBuilder.createQuery(QismtEmpInsuRate.class);
		Root<QismtEmpInsuRate> root = cq.from(QismtEmpInsuRate.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder
				.equal(root.get(QismtEmpInsuRate_.qismtEmpInsuRatePK).get(QismtEmpInsuRatePK_.ccd), companyCode.v()));
		lstpredicate.add(criteriaBuilder.le(root.get(QismtEmpInsuRate_.strYm), yearMonth.nextMonth().v()));
		lstpredicate.add(criteriaBuilder.ge(root.get(QismtEmpInsuRate_.endYm), yearMonth.previousMonth().v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtEmpInsuRate> query = em.createQuery(cq);
		return query.getResultList();
	}

	/**
	 * Find data by id.
	 *
	 * @param companyCode
	 *            the company code
	 * @param historyId
	 *            the history id
	 * @return the qismt emp insu rate
	 */
	public QismtEmpInsuRate findDataById(CompanyCode companyCode, String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtEmpInsuRate> cq = criteriaBuilder.createQuery(QismtEmpInsuRate.class);
		Root<QismtEmpInsuRate> root = cq.from(QismtEmpInsuRate.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder
				.equal(root.get(QismtEmpInsuRate_.qismtEmpInsuRatePK).get(QismtEmpInsuRatePK_.ccd), companyCode.v()));
		lstpredicate.add(criteriaBuilder
				.equal(root.get(QismtEmpInsuRate_.qismtEmpInsuRatePK).get(QismtEmpInsuRatePK_.histId), historyId));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtEmpInsuRate> query = em.createQuery(cq);
		return query.getSingleResult();
	}

	/**
	 * Checks if is invalid date range update.
	 *
	 * @param companyCode
	 *            the company code
	 * @param startMonth
	 *            the start month
	 * @param historyId
	 *            the history id
	 * @return true, if is invalid date range update
	 */
	@Override
	public boolean isInvalidDateRangeUpdate(CompanyCode companyCode, MonthRange monthRange, String historyId) {
		// start<=end
		if (monthRange.getStartMonth().v() > monthRange.getEndMonth().v()) {
			return true;
		}
		// data is begin update
		QismtEmpInsuRate qismtEmpInsuRate = findDataById(companyCode, historyId);
		if (qismtEmpInsuRate != null) {
			// begin update
			List<QismtEmpInsuRate> lstQismtEmpInsuRate = findBetweenUpdate(companyCode,
					YearMonth.of(qismtEmpInsuRate.getStrYm()), historyId);
			if (lstQismtEmpInsuRate == null || lstQismtEmpInsuRate.isEmpty()) {
				return false;
			}
			if (lstQismtEmpInsuRate.get(0).getStrYm() >= monthRange.getStartMonth().v()) {
				return true;
			}
			QismtEmpInsuRate qismtEmpInsuRateBeginUpdate = findDataById(companyCode,
					lstQismtEmpInsuRate.get(0).getQismtEmpInsuRatePK().getHistId());
			qismtEmpInsuRateBeginUpdate.setEndYm(monthRange.getStartMonth().previousMonth().v());
			update(toDomain(qismtEmpInsuRateBeginUpdate));
			return false;
		}
		return true;
	}

	/**
	 * Find between update.
	 *
	 * @param companyCode
	 *            the company code
	 * @param yearMonth
	 *            the year month
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	// Find end <= start order by start
	public List<QismtEmpInsuRate> findBetweenUpdate(CompanyCode companyCode, YearMonth yearMonth, String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtEmpInsuRate> cq = criteriaBuilder.createQuery(QismtEmpInsuRate.class);
		Root<QismtEmpInsuRate> root = cq.from(QismtEmpInsuRate.class);
		cq.select(root);
		List<Predicate> lstpredicate = new ArrayList<>();
		lstpredicate.add(criteriaBuilder
				.equal(root.get(QismtEmpInsuRate_.qismtEmpInsuRatePK).get(QismtEmpInsuRatePK_.ccd), companyCode.v()));
		lstpredicate.add(criteriaBuilder
				.notEqual(root.get(QismtEmpInsuRate_.qismtEmpInsuRatePK).get(QismtEmpInsuRatePK_.histId), historyId));
		lstpredicate.add(criteriaBuilder.le(root.get(QismtEmpInsuRate_.endYm), yearMonth.previousMonth().v()));
		cq.where(lstpredicate.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(QismtEmpInsuRate_.strYm)));
		TypedQuery<QismtEmpInsuRate> query = em.createQuery(cq);
		return query.getResultList();
	}

}
