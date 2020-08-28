package nts.uk.ctx.at.auth.dom.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceManagerImport {
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
	

}