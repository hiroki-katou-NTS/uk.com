package nts.uk.query.app.ccg005.query.comment;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformation;

/*
 * 社員のコメント情報 DTO
 */
@Data
public class EmployeeCommentInformationDto implements EmployeeCommentInformation.MementoSetter{

	// コメント
	private String comment;
	
	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
