package nts.uk.ctx.at.function.ac.statement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.statement.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.function.dom.statement.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class EmployeeGeneralInfoAdapterStateImpl implements EmployeeGeneralInfoAdapter{
	@Inject
	private EmployeeGeneralInfoPub employeeGeneralInfoPub;
	
	@Override
	public EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> employeeIds, DatePeriod period,boolean checkEmployment,
			boolean checkClassification, boolean checkJobTitle, boolean checkWorkplace, boolean checkDepartment) {
		EmployeeGeneralInfoDto dto = this.employeeGeneralInfoPub.getPerEmpInfo(employeeIds, period,true,true,true,true,true);
		
		
		List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports = dto.getWorkplaceDto().stream()
				.map(workPlaceHistory -> new ExWorkPlaceHistoryImport(workPlaceHistory.getEmployeeId(),
						workPlaceHistory.getWorkplaceItems().stream().map(workPlaceHistoryItem -> {
							return new ExWorkplaceHistItemImport(workPlaceHistoryItem.getHistoryId(),
									workPlaceHistoryItem.getPeriod(), workPlaceHistoryItem.getWorkplaceId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());
		
		return new EmployeeGeneralInfoImport(exWorkPlaceHistoryImports);
	}
}
