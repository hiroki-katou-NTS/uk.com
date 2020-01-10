package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterCommentCommand {
	private String reportId;
	private String approveComment;
}
