package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class Period.
 */
@Getter
@Setter
public class PeriodOutput {
	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/**
	 * Instantiates a new period.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 */
	public PeriodOutput(GeneralDate startDate, GeneralDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
