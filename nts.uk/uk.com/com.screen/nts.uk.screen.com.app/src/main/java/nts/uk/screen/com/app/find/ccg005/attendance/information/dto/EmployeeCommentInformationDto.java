package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/*
 * 社員のコメント情報 DTO
 */
@Data
@Builder
public class EmployeeCommentInformationDto {
	// コメント
	private String comment;
	
	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}