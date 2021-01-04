package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttConfirmEmpMonthDay {
	private boolean monthConfirm;
	private Integer monthApproval;
	private ApprovalRootStateImport_New approvalRootStateMonth;
	private List<PhaseApproverStt> monthApprovalLst;
	private List<DailyConfirmOutput> listDailyConfirm;
	private List<ApprovalRootStateImport_New> approvalRootStateDayLst;
	private Map<GeneralDate, List<PhaseApproverStt>> dayApprovalMap;
}
