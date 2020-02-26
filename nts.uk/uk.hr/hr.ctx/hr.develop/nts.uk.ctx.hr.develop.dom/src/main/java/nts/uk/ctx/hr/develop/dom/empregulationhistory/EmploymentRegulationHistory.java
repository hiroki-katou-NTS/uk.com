package nts.uk.ctx.hr.develop.dom.empregulationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
import nts.arc.time.calendar.period.DatePeriod;


/**
 * @author thanhpv
 * 就業規則の履歴
 */
@Getter
@AllArgsConstructor
public class EmploymentRegulationHistory extends AggregateRoot implements PersistentHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	/** 会社ID */
	private String companyId;
	
	/** 年月日期間の汎用履歴項目 */
	private List<DateHistoryItem> dateHistoryItem;

	@Override
	public List<DateHistoryItem> items() {
		// TODO Auto-generated method stub
		return dateHistoryItem;
	}

}
