package nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;

/**
 * The class AnyAggrPeriodExport.
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class AnyAggrPeriodExport implements AnyAggrPeriod.MementoGetter, AnyAggrPeriod.MementoSetter {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 任意集計枠コード
	 */
	private String aggrFrameCode;

	/**
	 * 任意集計名称
	 */
	private String optionalAggrName;

	/**
	 * 対象期間
	 */
	private DatePeriod period;

	/**
	 * No args constructor.
	 */
	private AnyAggrPeriodExport() {
	}

	/**
	 * From domain.
	 *
	 * @param domain the domain 任意集計期間
	 * @return the <code>AnyAggrPeriodExport</code>
	 */
	public static AnyAggrPeriodExport fromDomain(AnyAggrPeriod domain) {
		AnyAggrPeriodExport export = new AnyAggrPeriodExport();
		domain.setMemento(export);
		return export;
	}

	@Override
	public void setStartDate(GeneralDate startDate) {
		if (this.period == null) {
			this.period = new DatePeriod(startDate, null);
		} else {
			this.period = this.period.newSpan(startDate, this.period.end());
		}
	}

	@Override
	public void setEndDate(GeneralDate endDate) {
		if (this.period == null) {
			this.period = new DatePeriod(null, endDate);
		} else {
			this.period = this.period.newSpan(this.period.start(), endDate);
		}
	}

	@Override
	public GeneralDate getStartDate() {
		return this.period.start();
	}

	@Override
	public GeneralDate getEndDate() {
		return this.period.end();
	}

}
