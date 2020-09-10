package nts.uk.screen.at.app.ktgwidget.find.dto;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.bs.employee.app.find.employment.EmployeeBasicInfoExport;

@Data
@Builder
public class AcquisitionOfOvertimeHoursOfEmployeesDto {
	
	//	配下社員の個人情報
	private List<EmployeeBasicInfoExport> personalInformationOfSubordinateEmployees;
	
	//	配下社員の時間外時間
	private List<AgreementTimeDetail> OvertimeOfSubordinateEmployees;
}
