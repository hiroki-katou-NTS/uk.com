package nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.DateHistoryItem;
import nts.uk.shr.com.history.History;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 * 人事評価履歴
 */
@Getter
public class StressCheckHistory extends DomainEvent
		implements History<DateHistoryItem, DatePeriod, GeneralDate> {

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
