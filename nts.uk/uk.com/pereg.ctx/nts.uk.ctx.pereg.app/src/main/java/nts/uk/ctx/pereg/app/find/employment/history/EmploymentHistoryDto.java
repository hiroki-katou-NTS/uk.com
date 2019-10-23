package nts.uk.ctx.pereg.app.find.employment.history;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author sonnlb
 *
 */
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
	private Integer salarySegment;
	
	public EmploymentHistoryDto(String recordId, GeneralDate startDate, GeneralDate endDate, String employmentCode,
			Integer salarySegment) {
		super(recordId);
		this.startDate = startDate;
		this.endDate = endDate;
		this.employmentCode = employmentCode;
		this.salarySegment = salarySegment;
	}
	
	public static EmploymentHistoryDto createFromDomain(DateHistoryItem dateHistoryItem,
			EmploymentHistoryItem historyItem) {
		return new EmploymentHistoryDto(historyItem.getHistoryId(), dateHistoryItem.start(), dateHistoryItem.end(),
				historyItem.getEmploymentCode().v(), historyItem.getSalarySegment() != null? historyItem.getSalarySegment().value: null );
	}

}
