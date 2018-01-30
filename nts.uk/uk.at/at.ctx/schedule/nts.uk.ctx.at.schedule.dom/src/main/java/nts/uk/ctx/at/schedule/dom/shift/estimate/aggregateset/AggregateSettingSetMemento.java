package nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface AggregateSettingSetMemento.
 */
public interface AggregateSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the premium no.
	 *
	 * @param premiumNo the new premium no
	 */
	void setPremiumNo(List<ExtraTimeItemNo> premiumNo);
	
	/**
	 * Sets the monthly working day setting.
	 *
	 * @param monthlyWorkingDaySetting the new monthly working day setting
	 */
	void setMonthlyWorkingDaySetting(MonthlyWorkingDaySetting monthlyWorkingDaySetting);
}
