package nts.uk.screen.com.app.find.ccg005.attendance.information;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.status.ActivityStatus;

@Data
@Builder
public class ActivityStatusDto implements ActivityStatus.MementoSetter {
	// ステータス分類
	private Integer activity;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
