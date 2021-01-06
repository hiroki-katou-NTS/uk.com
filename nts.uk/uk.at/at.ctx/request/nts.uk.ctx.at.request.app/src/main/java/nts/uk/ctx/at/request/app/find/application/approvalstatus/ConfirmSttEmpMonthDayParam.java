package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttComfirmSet;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ConfirmSttEmpMonthDayParam {
	private String wkpID;
	private String startDate;
	private String endDate;
	private String empID;
	private ApprSttComfirmSet apprSttComfirmSet;
	private int yearMonth;
	private int closureId;
	private int closureDay;
	private boolean lastDayOfMonth;
}
