/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.util.ArrayList;
import java.util.List;
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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsuPK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu_;

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
		this.commandProxy().insertAll(toEntity(rate));
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
		// TODO Auto-generated method stub
		List<AccidentInsuranceRate> lstAccidentInsuranceRate = findAll(companyCode);
		for (AccidentInsuranceRate accidentInsuranceRate : lstAccidentInsuranceRate) {
			if (accidentInsuranceRate.getHistoryId().equals(historyId)) {
				return accidentInsuranceRate;
			}
		}
		return null;
	}

	@Override
	public boolean isInvalidDateRange(CompanyCode companyCode, YearMonth startMonth) {
		// TODO Auto-generated method stub
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

}
