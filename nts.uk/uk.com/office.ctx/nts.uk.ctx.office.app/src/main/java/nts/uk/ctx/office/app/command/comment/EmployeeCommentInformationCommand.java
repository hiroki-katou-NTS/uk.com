package nts.uk.ctx.office.app.command.comment;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformation;

/*
 * 社員のコメント情報 Command
 */
@Data
public class EmployeeCommentInformationCommand implements EmployeeCommentInformation.MementoGetter {
	// コメント
	private String comment;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
