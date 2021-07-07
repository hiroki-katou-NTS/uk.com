package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.HistoryItem;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.作業管理.作業初期選択設定
 * 作業初期選択項目
 * @author HieuLt
 */
@Getter
@AllArgsConstructor
public class TaskInitialSel extends  HistoryItem<DatePeriod, GeneralDate> {
	
	/** 社員ID **/ 
	private final String empID;
	
	/** 期間 **/
	private DatePeriod datePeriod;
	
	/** 作業項目 **/
	private TaskItem taskItem;

	@Override
	public DatePeriod span() {
		return datePeriod;
	}


	@Override
	public void changeSpan(DatePeriod newSpan) {
		datePeriod = newSpan;
		
	}

	@Override
	public String identifier() {
		return this.empID;
	}

	
}
