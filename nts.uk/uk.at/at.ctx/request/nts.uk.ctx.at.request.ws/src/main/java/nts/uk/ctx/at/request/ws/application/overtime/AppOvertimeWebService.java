package nts.uk.ctx.at.request.ws.application.overtime;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder_New;

/**
 * refactorKAF005_ver3
 * @author Doan Duy Hung
 *
 */

@Path("at/request/application/overtime")
@Produces("application/json")
public class AppOvertimeWebService {
	
	@Inject
	private AppOvertimeFinder_New appOvertimeFinder;
	
	public void startNew(AppOvertimeStartParam startParam) {
		appOvertimeFinder.startNew(
				startParam.overtimeAtr, 
				startParam.rootAtr, 
				startParam.employeeLst, 
				startParam.appDate, 
				startParam.startTime, 
				startParam.endTime, 
				startParam.appReason, 
				startParam.prePostAtr);
	}
	
	public void changeAppDate(OvertimeChangeDateParam changeDateParam) {
		appOvertimeFinder.changeAppDate(
				changeDateParam.overtimeAtr, 
				changeDateParam.rootAtr, 
				changeDateParam.overtimeInstructionAtr, 
				changeDateParam.employeeID, 
				changeDateParam.appDate, 
				changeDateParam.startTime, 
				changeDateParam.endTime, 
				changeDateParam.prePostAtr);
	}
	
	public void changeWorkType() {
		
	}
	
	public void changeTime() {
		
	}
	
	public void changePrePost() {
		
	}
	
	public void calculate() {
		
	}
	
	public void register() {
		
	}
	
	public void startDetail() {
		
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
	
}
