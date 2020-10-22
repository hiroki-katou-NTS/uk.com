package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;

@Getter
public class SaveOptionalAggrPeriodCommand implements AnyAggrPeriod.MementoGetter {

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;

	/**
	 * No args constructor.
	 */
	private SaveOptionalAggrPeriodCommand() {
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return null;
	}

	/**
	 * Gets period.
	 *
	 * @return the period
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.startDate, this.endDate);
	}

}
