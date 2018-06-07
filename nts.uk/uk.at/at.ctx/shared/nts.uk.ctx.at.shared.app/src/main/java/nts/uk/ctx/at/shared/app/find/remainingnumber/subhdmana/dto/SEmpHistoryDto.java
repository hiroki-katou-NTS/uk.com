package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
public class SEmpHistoryDto {
	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The job title code. */
	// 雇用コード
	private String employmentCode;

	/** The job title name. */
	// 雇用名称
	private String employmentName;

	/** The period. */
	// 配属期間 
	private GeneralDate startDate;
	private GeneralDate endDate;
	
	public static SEmpHistoryDto convertToDto(SEmpHistoryImport empHistory){
		return new SEmpHistoryDto(empHistory.getEmployeeId(), empHistory.getEmploymentCode(), empHistory.getEmploymentName(), empHistory.getPeriod().start(), empHistory.getPeriod().end());
	}
}
