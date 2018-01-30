package nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface AggregateSettingGetMemento.
 */
public interface AggregateSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the premium no.
	 *
	 * @return the premium no
	 */
	List<ExtraTimeItemNo> getPremiumNo();
	
	/**
	 * Gets the monthly working day setting.
	 *
	 * @return the monthly working day setting
	 */
	MonthlyWorkingDaySetting getMonthlyWorkingDaySetting();
}
