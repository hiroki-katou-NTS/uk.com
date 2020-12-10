package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttExecutionParam {
	private int closureId;
	private String startDate;
	private String endDate;
	private List<DisplayWorkplace> wkpInfoLst;
	private InitDisplayOfApprovalStatus initDisplayOfApprovalStatus;
}
