package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.HistoryItem;

/**
 * 承認ルート履歴
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentAppHistoryItem extends HistoryItem<DatePeriod, GeneralDate> {
	
	/** 履歴ID */
	private String historyId;

	/** 期間 */
	private DatePeriod datePeriod;
	
	/**
	 * [C-1] 期間で作成する
	 * @param datePeriod 期間
	 */
	public EmploymentAppHistoryItem(DatePeriod datePeriod) {
		this.datePeriod = datePeriod;
		this.historyId = UUID.randomUUID().toString();
	}

	@Override
	public DatePeriod span() {
		return datePeriod;
	}

	@Override
	public String identifier() {
		return historyId;
	}

	@Override
	public void changeSpan(DatePeriod newSpan) {
		datePeriod = newSpan;
	}

}
