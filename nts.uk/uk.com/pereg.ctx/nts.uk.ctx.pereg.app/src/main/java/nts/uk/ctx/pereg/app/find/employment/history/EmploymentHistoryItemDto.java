package nts.uk.ctx.pereg.app.find.employment.history;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

//雇用履歴項目
/**
 * @author sonnlb
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmploymentHistoryItemDto extends PeregDomainDto {

	// 給与区分
	@PeregItem("IS00068")
	private int salarySegment;

	// 雇用コード.
	@PeregItem("IS00069")
	private String employmentCode;

	public EmploymentHistoryItemDto(String recordId, String employeeId, int salarySegment, String employmentCode) {
		super(recordId, employeeId, null);
		this.salarySegment = salarySegment;
		this.employmentCode = employmentCode;
	}

	public static EmploymentHistoryItemDto createFromDomain(EmploymentHistoryItem domain) {
		return new EmploymentHistoryItemDto(domain.getHistoryId(), domain.getEmployeeId(),
				domain.getSalarySegment().value, domain.getEmploymentCode().v());
	}
}
