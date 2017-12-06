package nts.uk.ctx.at.auth.dom.wkpmanager;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Getter
public class WorkplaceManager extends AggregateRoot {
	/**
	 * 職場管理者ID
	 */
	private String workplaceManagerId;
	/**
	 * 社員ID
	 */
	private String employeeId;
	/**
	 * 職場ID
	 */
	private String workplaceId;
	/**
	 * 履歴期間
	 */
	private DatePeriod historyPeriod;
	
	public WorkplaceManager(String workplaceManagerId, String employeeId, String workplaceId, DatePeriod historyPeriod) {
		super();
		this.workplaceManagerId = workplaceManagerId;
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
		this.historyPeriod = historyPeriod;
	}
}
