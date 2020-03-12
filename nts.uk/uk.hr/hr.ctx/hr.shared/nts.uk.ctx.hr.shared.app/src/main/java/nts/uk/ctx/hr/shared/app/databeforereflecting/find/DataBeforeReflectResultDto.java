package nts.uk.ctx.hr.shared.app.databeforereflecting.find;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;

@Builder
@Data
public class DataBeforeReflectResultDto {
	// List<定年退職予定者>
	private List<RetiredEmployeeInfoResult> retiredEmployees;
	// 社員情報のリスト
	private List<EmployeeInformationImport> employeeImports;
}
