package nts.uk.ctx.office.app.command.status;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.status.ActivityStatus;

/*
 * 在席のステータス Command
 */
@Data
public class ActivityStatusCommand implements ActivityStatus.MementoGetter {
	// ステータス分類
	private Integer activity;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
