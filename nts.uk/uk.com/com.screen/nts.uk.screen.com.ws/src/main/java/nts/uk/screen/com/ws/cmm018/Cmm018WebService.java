package nts.uk.screen.com.ws.cmm018;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ApproverOperationSettingsDto;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ChangeOperationModeCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ChangeOperationModeCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.RegistrationScreenSettingsCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.RegistrationScreenSettingsCommandHandler;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeSearch;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.screen.com.app.find.cmm018.InitRegistationScreenSettingDto;
import nts.uk.screen.com.app.find.cmm018.ScreenQueryApprovalAuthorityEmp;
import nts.uk.screen.com.app.find.cmm018.ScreenQueryFindBootMode;
import nts.uk.screen.com.app.find.cmm018.ScreenQueryInitRegistationScreenSetting;
import nts.uk.shr.com.context.AppContexts;

@Path("screen/approvermanagement/workroot")
@Produces("application/json")
public class Cmm018WebService {
	
	@Inject
	private PersonAdapter psInfor;
	
	@Inject
	private ScreenQueryApprovalAuthorityEmp screenQueryApprovalAuthorityEmp;
	
	@Inject
	private ScreenQueryFindBootMode screenQueryFindBootMode;
	
	@Inject
	private ScreenQueryInitRegistationScreenSetting screenQueryInitRegistation;
	
	@Inject
	private RegistrationScreenSettingsCommandHandler registrationScreenSettingsCommandHandler;
	
	@Inject
	private ChangeOperationModeCommandHandler changeOperationModeCommandHandler;
	
	@POST
	@Path("getEmployeesInfo")
	public List<EmployeeImport> findByWpkIds(EmployeeSearch employeeSearch) {
		
		List<String> empIds = 
				screenQueryApprovalAuthorityEmp.get(
						employeeSearch.getWorkplaceIds(),
						employeeSearch.getBaseDate(),
						employeeSearch.getSysAtr());
		String cid = AppContexts.user().companyId();
		
		List<EmployeeImport> lstEmpDto = 
				empIds.stream()
					  .map(x -> {
						  	PersonImport perInfo = psInfor.getPersonInfo(x);
							EmployeeImport emplpyeeImport = new EmployeeImport(
									cid,
									"",
									x,
									perInfo.getEmployeeCode(),
									perInfo.getEmployeeName(),
									"",
									"",
									"",
									null,
									null);
							return emplpyeeImport;
					  })
					  .collect(Collectors.toList());

		return lstEmpDto;
	}
	
	@POST
	@Path("check-boot-mode")
	public ApproverOperationSettingsDto checkBootMode() {
		return this.screenQueryFindBootMode.get();
	}
	
	@POST
	@Path("init-registation-screen-setting")
	public InitRegistationScreenSettingDto initRegistationScreenSetting() {
		return this.screenQueryInitRegistation.get();
	}
	
	@POST
	@Path("registration-screen-settings")
	public void registrationScreenSettings(RegistrationScreenSettingsCommand command) {
		this.registrationScreenSettingsCommandHandler.handle(command);
	}
	
	@POST
	@Path("change-operation-mode")
	public void changeOperationMode(ChangeOperationModeCommand command) {
		this.changeOperationModeCommandHandler.handle(command);
	}
}
