/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.unemployeerate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
	public UnemployeeInsuranceRate findById(String id, String companyCode) {
		List<UnemployeeInsuranceRate> lstUnemployeeInsuranceRate = findAll(companyCode);
		for (UnemployeeInsuranceRate unemployeeInsuranceRate : lstUnemployeeInsuranceRate) {
			if (unemployeeInsuranceRate.getHistoryId().equals(id)) {
				return unemployeeInsuranceRate;
			}
		}
		// TODO Auto-generated method stub
		return null;
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
	 * UnemployeeInsuranceRateRepository#findAll(java.lang.String)
	 */
	@Override
	public List<UnemployeeInsuranceRate> findAll(String companyCode) {
		List<UnemployeeInsuranceRate> lstUnemployeeInsuranceRate = new ArrayList<>();
		MonthRange monthRange006 = MonthRange.range(new YearMonth(2016 * 100 + 4), new YearMonth(9999 * 100 + 12));
		lstUnemployeeInsuranceRate.add(fromDomain("historyId006", companyCode, monthRange006));
		MonthRange monthRange005 = MonthRange.range(new YearMonth(2015 * 100 + 10), new YearMonth(2016 * 100 + 3));
		lstUnemployeeInsuranceRate.add(fromDomain("historyId005", companyCode, monthRange005));
		MonthRange monthRange004 = MonthRange.range(new YearMonth(2015 * 100 + 4), new YearMonth(2015 * 100 + 9));
		lstUnemployeeInsuranceRate.add(fromDomain("historyId004", companyCode, monthRange004));
		MonthRange monthRange003 = MonthRange.range(new YearMonth(2014 * 100 + 9), new YearMonth(2015 * 100 + 3));
		lstUnemployeeInsuranceRate.add(fromDomain("historyId003", companyCode, monthRange003));
		MonthRange monthRange002 = MonthRange.range(new YearMonth(2014 * 100 + 4), new YearMonth(2014 * 100 + 8));
		lstUnemployeeInsuranceRate.add(fromDomain("historyId002", companyCode, monthRange002));
		MonthRange monthRange001 = MonthRange.range(new YearMonth(2013 * 100 + 4), new YearMonth(2014 * 100 + 3));
		lstUnemployeeInsuranceRate.add(fromDomain("historyId001", companyCode, monthRange001));
		return lstUnemployeeInsuranceRate;
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
	public UnemployeeInsuranceRate fromDomain(String historyId, String companyCode, MonthRange monthRange) {
		return new UnemployeeInsuranceRate(new UnemployeeInsuranceRateGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return 11L;
			}

			@Override
			public Set<UnemployeeInsuranceRateItem> getRateItems() {
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
}
