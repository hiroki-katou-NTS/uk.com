package nts.uk.ctx.at.record.dom.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkplaceConfigHistory {
	//履歴ID
	private String historyId;
	private DatePeriod period;
}