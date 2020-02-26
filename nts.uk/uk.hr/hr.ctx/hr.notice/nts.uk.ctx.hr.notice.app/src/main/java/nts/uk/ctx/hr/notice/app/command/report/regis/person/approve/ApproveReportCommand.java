package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproveReportCommand {
	private String reportId;
	private String rootInstanceId;
	private String approveComment;
	private int actionApprove;
}
