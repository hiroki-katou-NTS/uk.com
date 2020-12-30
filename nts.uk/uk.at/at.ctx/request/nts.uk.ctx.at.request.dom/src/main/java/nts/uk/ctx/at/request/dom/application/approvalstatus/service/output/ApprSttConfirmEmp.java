package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttConfirmEmp {
	private List<DailyConfirmOutput> listDailyConfirm;
	private String empCD;
	private String empName;
	private Boolean monthConfirm;
	private Integer monthApproval;
	private String empID;
}
