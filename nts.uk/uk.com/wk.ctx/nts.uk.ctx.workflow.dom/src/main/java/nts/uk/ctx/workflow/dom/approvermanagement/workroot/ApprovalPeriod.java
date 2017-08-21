package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.time.LocalDate;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class Approval Period (期間).
 */
@Data
public class ApprovalPeriod {

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/**
	 * Instantiates a new period.
	 */
	public ApprovalPeriod() {
		super();
	}

	/**
	 * Instantiates a new period.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public ApprovalPeriod(GeneralDate startDate, GeneralDate endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public static ApprovalPeriod createSimpleFromJavaType(String startDate, String endDate){
		return new ApprovalPeriod(GeneralDate.localDate(LocalDate.parse(startDate)), 
									GeneralDate.localDate(LocalDate.parse(endDate)));
	}

}
