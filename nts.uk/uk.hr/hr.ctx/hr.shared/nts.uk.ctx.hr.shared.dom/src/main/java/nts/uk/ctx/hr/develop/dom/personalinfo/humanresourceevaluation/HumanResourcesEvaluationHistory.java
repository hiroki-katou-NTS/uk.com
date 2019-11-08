package nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 * 人事評価履歴
 */
@Getter
public class HumanResourcesEvaluationHistory extends DomainEvent
		implements UnduplicatableHistory<DateHistoryItem, DatePeriod, GeneralDate> {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 履歴項目
	 */
	private List<DateHistoryItem> careerTypeHistory;

	/**
	 * 社員ID
	 */
	private String careerTypeId;

	@Override
	public List<DateHistoryItem> items() {
		return careerTypeHistory;
	}

}
