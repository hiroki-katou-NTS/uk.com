package nts.uk.query.app.comment;

import lombok.Data;
import nts.arc.time.GeneralDate;

/*
 * 社員のコメント情報 DTO
 */
@Data
public class EmployeeCommentInformationDto {

	// コメント
	private String comment;
	

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
