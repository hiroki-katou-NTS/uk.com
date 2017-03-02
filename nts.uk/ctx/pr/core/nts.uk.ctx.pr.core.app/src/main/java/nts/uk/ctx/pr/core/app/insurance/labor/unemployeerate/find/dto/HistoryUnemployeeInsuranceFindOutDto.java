/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto;

import java.util.Set;

import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateSetMemento;

/**
 * The Class HistoryUnemployeeInsuranceFindOutDto.
 */
@Data
public class HistoryUnemployeeInsuranceFindOutDto implements UnemployeeInsuranceRateSetMemento {

	/** The history id. */
	private String historyId;

	/** The start month rage. */
	private String startMonthRage;

	/** The end month rage. */
	private String endMonthRage;

	/** The infor month rage. */
	private String inforMonthRage;

	/**
	 * Convert month.
	 *
	 * @param yearMonth
	 *            the year month
	 * @return the string
	 */
	public static String convertMonth(YearMonth yearMonth) {
		String convert = null;
		String mounth = null;
		if (yearMonth.month() < 10) {
			mounth = "0" + yearMonth.month();
		} else {
			mounth = String.valueOf(yearMonth.month());
		}
		convert = yearMonth.year() + "/" + mounth;
		return convert;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setCompanyCode(nts.uk.ctx.core.dom.
	 * company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setApplyRange(nts.uk.ctx.pr.core.dom.
	 * insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.endMonthRage = convertMonth(applyRange.getEndMonth());
		this.startMonthRage = convertMonth(applyRange.getStartMonth());
		this.inforMonthRage = this.startMonthRage + " ~ " + this.endMonthRage;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<UnemployeeInsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
}
