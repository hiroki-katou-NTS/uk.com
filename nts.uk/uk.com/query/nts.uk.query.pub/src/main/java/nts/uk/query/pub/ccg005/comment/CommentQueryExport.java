package nts.uk.query.pub.ccg005.comment;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Builder
@Data
public class CommentQueryExport {
	// コメント
	private String comment;
	
	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
