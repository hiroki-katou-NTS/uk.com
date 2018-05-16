package nts.uk.ctx.at.record.dom.adapter.query.employee;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeSearchInfoDto {

	/** The employee id. */
	private String employeeId; // 社員ID

	/** The employee code. */
	private String employeeCode; // 社員コード

	/** The employee name. */
	private String employeeName; // 氏名
	
	private List<HistoryCommonInfo> classifications;
	
	private List<HistoryCommonInfo> businessTypes;
	
	private List<HistoryCommonInfo> jobTitles;
	
	private List<HistoryCommonInfo> workplaces;
	
	private List<HistoryCommonInfo> employments;
	
}
