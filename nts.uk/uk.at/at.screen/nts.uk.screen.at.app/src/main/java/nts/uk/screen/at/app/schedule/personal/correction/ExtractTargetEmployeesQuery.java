package nts.uk.screen.at.app.schedule.personal.correction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 対象社員を抽出する
 */
@Stateless
public class ExtractTargetEmployeesQuery {

	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;

	public List<EmployeeInfoDto> get(GeneralDate referDate, TargetOrgIdenInfor target) {
		
		
		// 2 create param
		EmployeeInformationQueryDtoImport param = new EmployeeInformationQueryDtoImport(
				new ArrayList<>(Arrays.asList(AppContexts.user().employeeId())), referDate, false, false, false, false,
				false, false);

		// 3 <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmpInfo = employeeInformationAdapter.getEmployeeInfo(param);
		
		

		return null;
	}

	@Data
	public class EmployeeInfoDto {
		private String employeeId;
		private String employeeCd;
		private String businessName;
	}
}
