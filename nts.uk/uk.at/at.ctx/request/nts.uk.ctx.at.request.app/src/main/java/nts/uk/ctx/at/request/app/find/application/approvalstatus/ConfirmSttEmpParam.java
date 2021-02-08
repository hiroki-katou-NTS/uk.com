package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

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
public class ConfirmSttEmpParam {
	private String wkpID;
	private String startDate;
	private String endDate;
	private List<EmpPeriodParam> empPeriodLst;
	private ApprSttComfirmSet apprSttComfirmSet;
	private int yearMonth;
	private int closureId;
	private int closureDay;
	private boolean lastDayOfMonth;
}
