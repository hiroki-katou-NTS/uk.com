package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.interview.service.InterviewSummary;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;

@Builder
@Data
public class SearchRetiredResultDto {
	// 処理結果
	private InterviewSummary interView;
	// List<定年退職予定者>
	private List<PlannedRetirementDto> retiredEmployees;
	// 社員情報のリスト
	private List<EmployeeInformationImport> employeeImports;
}
