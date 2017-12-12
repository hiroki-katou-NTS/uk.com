package nts.uk.ctx.pereg.app.find.employment.history;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentHistoryDto extends PeregDomainDto {

	// 開始日
	@PeregItem("IS00066")
	private GeneralDate startDate;

	// 終了日
	@PeregItem("IS00067")
	private GeneralDate endDate;

	// 雇用コード.
	@PeregItem("IS00068")
	private String employmentCode;

	// 給与区分
	@PeregItem("IS00069")
	private int salarySegment;
	
	public static EmploymentHistoryDto createFromDomain(EmploymentHistory history, EmploymentHistoryItem historyItem) {
		return new EmploymentHistoryDto(history.getHistoryItems().get(0).start(),
				history.getHistoryItems().get(0).end(), historyItem.getEmploymentCode().v(),
				historyItem.getSalarySegment().value);
	}

}
