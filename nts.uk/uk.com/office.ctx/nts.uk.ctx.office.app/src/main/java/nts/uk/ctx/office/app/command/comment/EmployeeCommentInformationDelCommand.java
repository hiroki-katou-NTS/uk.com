package nts.uk.ctx.office.app.command.comment;

import lombok.Data;
import nts.arc.time.GeneralDate;

/*
 * 社員のコメント情報 Command
 */
@Data
public class EmployeeCommentInformationDelCommand {
	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
