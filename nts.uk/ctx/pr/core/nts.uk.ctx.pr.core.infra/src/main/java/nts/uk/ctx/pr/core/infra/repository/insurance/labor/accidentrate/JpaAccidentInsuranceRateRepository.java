/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
	public List<AccidentInsuranceRate> findAll(String companyCode) {
		List<AccidentInsuranceRate> lstAccidentInsuranceRate = new ArrayList<>();
		MonthRange monthRange006 = MonthRange.range(new YearMonth(2016 * 100 + 4), new YearMonth(9999 * 100 + 12));
		lstAccidentInsuranceRate.add(fromDomain("historyId006", companyCode, monthRange006));
		MonthRange monthRange005 = MonthRange.range(new YearMonth(2015 * 100 + 10), new YearMonth(2016 * 100 + 3));
		lstAccidentInsuranceRate.add(fromDomain("historyId005", companyCode, monthRange005));
		MonthRange monthRange004 = MonthRange.range(new YearMonth(2015 * 100 + 4), new YearMonth(2015 * 100 + 9));
		lstAccidentInsuranceRate.add(fromDomain("historyId004", companyCode, monthRange004));
		MonthRange monthRange003 = MonthRange.range(new YearMonth(2014 * 100 + 9), new YearMonth(2015 * 100 + 3));
		lstAccidentInsuranceRate.add(fromDomain("historyId003", companyCode, monthRange003));
		MonthRange monthRange002 = MonthRange.range(new YearMonth(2014 * 100 + 4), new YearMonth(2014 * 100 + 8));
		lstAccidentInsuranceRate.add(fromDomain("historyId002", companyCode, monthRange002));
		MonthRange monthRange001 = MonthRange.range(new YearMonth(2013 * 100 + 4), new YearMonth(2014 * 100 + 3));
		lstAccidentInsuranceRate.add(fromDomain("historyId001", companyCode, monthRange001));
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
	public AccidentInsuranceRate fromDomain(String historyId, String companyCode, MonthRange monthRange) {
		return new AccidentInsuranceRate(new AccidentInsuranceRateGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return 11L;
			}

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
				return new CompanyCode(companyCode);
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
	public AccidentInsuranceRate findById(String companyCode, String historyId) {
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
	public boolean isInvalidDateRange(YearMonth startMonth) {
		// TODO Auto-generated method stub
		return false;
	}

}
