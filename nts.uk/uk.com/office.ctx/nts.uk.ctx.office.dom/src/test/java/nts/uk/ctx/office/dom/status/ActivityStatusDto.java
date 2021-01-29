package nts.uk.ctx.office.dom.status;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class ActivityStatusDto implements ActivityStatus.MementoSetter, ActivityStatus.MementoGetter {
	// ステータス分類
	private Integer activity;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
