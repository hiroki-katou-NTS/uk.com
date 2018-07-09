package nts.uk.ctx.at.request.ws.application.employmentsystem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.employment.EmploymentReserveLeaveInfor;
import nts.uk.ctx.at.request.app.find.application.employment.EmploymentReserveLeaveInforFinder;

@Path("at/request/application/employment")
@Produces("application/json")
public class EmploymentSystemWebservice extends WebService {
	@Inject
	private EmploymentReserveLeaveInforFinder employmentReserveLeaveInforFinder;
	
	@POST
	@Path("system")
	public EmploymentReserveLeaveInfor getEmploymentSetting(Param param){
		 return employmentReserveLeaveInforFinder.getEmploymentReserveLeaveInfor(param.isMode(), param.getEmployeeIDs(), param.getInputDate());
	}
	@POST
	@Path("getByEmployee")
	public EmploymentReserveLeaveInfor getByEmployee(Param param){
		 return employmentReserveLeaveInforFinder.getByEmloyee(param.isMode(), param.getEmployeeIDs(), param.getInputDate());
	}
	
}
@Data
class Param{
	/**
	 * 選択モード：単一選択　or　複数選択    
	 */
	private boolean mode;
	/** 基準日：年月日 */
	private String inputDate;
	/** 選択済項目 */
	private List<String> employeeIDs;
}
