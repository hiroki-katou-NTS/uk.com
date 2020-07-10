package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SEmpHistImportDto {
	
	/** The employee id. */
	// 社員ID
	public String employeeId;

	/** The job title code. */
	// 雇用コード
	public String employmentCode;

	/** The job title name. */
	// 雇用名称
	public String employmentName;

	/** The period. */
	// 配属期間 
	public String startDate;
	public String endDate;
	
	public static SEmpHistImportDto fromDomain(SEmpHistImport empHistImport) {
		return new SEmpHistImportDto(
				empHistImport.getEmployeeId(), 
				empHistImport.getEmploymentCode(), 
				empHistImport.getEmploymentName(), 
				empHistImport.getPeriod().start().toString("yyyy/MM/dd"), 
				empHistImport.getPeriod().end().toString("yyyy/MM/dd"));
	}
	
	public SEmpHistImport toDomain() {
		SEmpHistImport result = new SEmpHistImport();
		result.setEmployeeId(employeeId);
		result.setEmploymentCode(employmentCode);
		result.setEmploymentName(employmentName);
		result.setPeriod(new DatePeriod(GeneralDate.fromString(startDate, "yyyy/MM/dd"), GeneralDate.fromString(endDate, "yyyy/MM/dd")));
		return result;
	}
}
