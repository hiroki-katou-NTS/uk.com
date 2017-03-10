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
import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
import nts.gul.text.IdentifierUtil;
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
		this.commandProxy().insertAll(this.ramdomHistory(rate));
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
		this.commandProxy().updateAll(this.toEntity(rate));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String companyCode, String historyId, long version) {
		List<QismtWorkAccidentInsu> lstRateRemove = this.findDataById(companyCode, historyId);
		this.commandProxy().removeAll(lstRateRemove);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findAll(int)
	 */
	@Override
	public List<AccidentInsuranceRate> findAll(String companyCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QISMT_WORK_ACCIDENT_INSU (QismtWorkAccidentInsu SQL)
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);

		// root data
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq String
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode));

		// eq type business 1st
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// set order
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));

		// exclude select
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<AccidentInsuranceRate> lstAccidentInsuranceRate = query.getResultList().stream()
				.map(item -> this.toDomainHistory(item)).collect(Collectors.toList());
		return lstAccidentInsuranceRate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<AccidentInsuranceRate> findById(String String, String historyId) {
		return Optional.ofNullable(this.toDomain(this.findDataById(String, historyId)));
	}

	/**
	 * Find data by id.
	 *
	 * @param String
	 *            the company code
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	public List<QismtWorkAccidentInsu> findDataById(String companyCode, String historyId) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QISMT_WORK_ACCIDENT_INSU (QismtWorkAccidentInsu SQL)
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);

		// root data
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq String
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode));

		// eq historyId
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.histId),
				historyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		// set order
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));
		// exclude select
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = query.getResultList();
		return lstQismtWorkAccidentInsu;
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
		for (int i = JpaAccidentInsuranceRateRepository.BEGIN_FIRST; i < JpaAccidentInsuranceRateRepository.SIZE_TEN; i++) {
			lstQismtWorkAccidentInsu.add(new QismtWorkAccidentInsu());
		}
		rate.saveToMemento(new JpaAccidentInsuranceRateSetMemento(lstQismtWorkAccidentInsu));
		return lstQismtWorkAccidentInsu;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the accident insurance rate
	 */
	public AccidentInsuranceRate toDomain(List<QismtWorkAccidentInsu> entity) {
		return new AccidentInsuranceRate(new JpaAccidentInsuranceRateGetMemento(entity));
	}

	/**
	 * To domain history.
	 *
	 * @param entity
	 *            the entity
	 * @return the accident insurance rate
	 */
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
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = this.toEntity(rate);
		String historyId = IdentifierUtil.randomUniqueId();
		for (QismtWorkAccidentInsu qismtWorkAccidentInsu : lstQismtWorkAccidentInsu) {
			QismtWorkAccidentInsuPK pk = qismtWorkAccidentInsu.getQismtWorkAccidentInsuPK();
			pk.setHistId(historyId);
			qismtWorkAccidentInsu.setQismtWorkAccidentInsuPK(pk);
		}
		return lstQismtWorkAccidentInsu;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findFirstData(nts.uk.ctx.core.dom.company
	 * .String)
	 */
	@Override
	public Optional<AccidentInsuranceRate> findFirstData(String companyCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QISMT_WORK_ACCIDENT_INSU (QismtWorkAccidentInsu SQL)
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);

		// root data
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq String
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode));

		// eq business type 1st
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// set order
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));

		// exclude select
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = query.getResultList();

		if (ListUtil.isEmpty(lstQismtWorkAccidentInsu)) {
			return Optional.empty();
		}

		return this.findById(companyCode,
				lstQismtWorkAccidentInsu.get(JpaAccidentInsuranceRateRepository.BEGIN_FIRST).getQismtWorkAccidentInsuPK().getHistId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#updateYearMonth(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.accidentrate.AccidentInsuranceRate,
	 * nts.arc.time.YearMonth)
	 */
	@Override
	public void updateYearMonth(AccidentInsuranceRate rate, YearMonth yearMonth) {
		// to entity
		List<QismtWorkAccidentInsu> lstEntity = this.toEntity(rate);
		for (QismtWorkAccidentInsu entity : lstEntity) {
			entity.setEndYm(yearMonth.v());
		}
		this.update(this.toDomain(lstEntity));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateRepository#findBetweenUpdate(nts.uk.ctx.core.dom.
	 * company.String, nts.arc.time.YearMonth, java.lang.String)
	 */
	@Override
	public Optional<AccidentInsuranceRate> findBetweenUpdate(String companyCode, YearMonth yearMonth,
			String historyId) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QISMT_WORK_ACCIDENT_INSU (QismtWorkAccidentInsu SQL)
		CriteriaQuery<QismtWorkAccidentInsu> cq = criteriaBuilder.createQuery(QismtWorkAccidentInsu.class);

		// root data
		Root<QismtWorkAccidentInsu> root = cq.from(QismtWorkAccidentInsu.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq String
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.ccd),
				companyCode));

		// not eq historyId
		lstpredicateWhere.add(criteriaBuilder.notEqual(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.histId),
				historyId));

		// eq business type 1st
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(QismtWorkAccidentInsu_.qismtWorkAccidentInsuPK).get(QismtWorkAccidentInsuPK_.waInsuCd),
				BusinessTypeEnum.Biz1St.value));

		// le start year month
		lstpredicateWhere.add(criteriaBuilder.le(root.get(QismtWorkAccidentInsu_.strYm), yearMonth.v()));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// set order
		cq.orderBy(criteriaBuilder.desc(root.get(QismtWorkAccidentInsu_.strYm)));

		// exclude select
		TypedQuery<QismtWorkAccidentInsu> query = em.createQuery(cq);
		List<QismtWorkAccidentInsu> lstQismtWorkAccidentInsu = query.getResultList();

		if (ListUtil.isEmpty(lstQismtWorkAccidentInsu)) {
			return Optional.empty();
		}

		return this.findById(companyCode,
				lstQismtWorkAccidentInsu.get(JpaAccidentInsuranceRateRepository.BEGIN_FIRST).getQismtWorkAccidentInsuPK().getHistId());
	}

}
