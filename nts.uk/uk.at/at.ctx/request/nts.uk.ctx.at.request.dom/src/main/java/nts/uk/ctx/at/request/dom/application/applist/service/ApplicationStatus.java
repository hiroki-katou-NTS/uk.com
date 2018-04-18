package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * tam thoi /output cua 4
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationStatus {
	private int unApprovalNumber;
	private int approvalNumber;
	private int approvalAgentNumber;
	private int cancelNumber;
	private int remandNumner;
	private int denialNumber;
}
