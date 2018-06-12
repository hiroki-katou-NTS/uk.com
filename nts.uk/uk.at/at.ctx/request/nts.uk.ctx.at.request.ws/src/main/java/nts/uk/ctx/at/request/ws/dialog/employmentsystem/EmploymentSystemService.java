package nts.uk.ctx.at.request.ws.dialog.employmentsystem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmployeeBasicInfoDto;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmploymentSystemFinder;

@Path("at/request/dialog/employmentsystem")
@Produces("application/json")
public class EmploymentSystemService extends WebService {
	@Inject
	EmploymentSystemFinder employeeFinder;
	
	@POST
	@Path("getEmployee/{param}")
	public List<EmployeeBasicInfoDto> getEmployee(EmployeeParam param)
	{		
		// アルゴリズム「振休確認ダイアログ開始」を実行する
		return employeeFinder.getEmployee(param.getEmployeeIds(), param.getBaseDate());
	}
	
	@Data
	class EmployeeParam{
		 List<String> employeeIds;
		 String baseDate;
	}
}
