package nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExWorkTypeHisItemImport {

	/** 履歴ID */
	private String historyId;

	/** 期間 */
	private DatePeriod period;

	/** 勤務種別コード */
	private String businessTypeCd;
}
