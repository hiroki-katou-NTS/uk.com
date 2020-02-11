package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.DateHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.arc.time.calendar.period.DatePeriod;

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
	private String employeeId;

	@Override
	public List<DateHistoryItem> items() {
		return careerTypeHistory;
	}

	public HumanResourcesEvaluationHistory(String companyId, List<DateHistoryItem> careerTypeHistory,
			String employeeId) {
		super();
		this.companyId = companyId;
		this.careerTypeHistory = careerTypeHistory;
		this.employeeId = employeeId;
	}
	
	

}
