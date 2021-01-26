package nts.uk.ctx.office.dom.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class ActivityStatusDto implements ActivityStatus.MementoSetter, ActivityStatus.MementoGetter {
	// ステータス分類
	private Integer activity;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;
}
