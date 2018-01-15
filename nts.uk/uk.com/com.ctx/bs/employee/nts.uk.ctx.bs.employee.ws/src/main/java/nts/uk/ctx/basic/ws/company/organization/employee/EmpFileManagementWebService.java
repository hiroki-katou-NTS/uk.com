package nts.uk.ctx.basic.ws.company.organization.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.AddEmpAvaOrMapCommandHandler;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.AddEmpDocumentFileCommand;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.EmpAvaOrMapCommand;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.EmpDocumentFileCommandHandler;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.RemoveDocumentFileEmpCommandHandler;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.RemoveEmpAvaOrMapCommandHandler;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.UpdateCtgDocFileCommandHandler;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.UpdateCtgDocFileDocumentFileCommand;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.UpdateEmpAvaOrMapCommandHandler;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.EmployeeFileManagementFinder;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;

@Path("basic/organization/empfilemanagement")
@Produces({ "application/json", "text/plain" })
public class EmpFileManagementWebService extends WebService {

	@Inject
	EmployeeFileManagementFinder employeeFileManagementFinder;
	
	@Inject
	EmpDocumentFileCommandHandler empDocumentFileCommandHandler; 
	
	@Inject
	AddEmpAvaOrMapCommandHandler addEmpAvaOrMapCommandHandler;

	@Inject
	UpdateEmpAvaOrMapCommandHandler updateEmpAvaOrMapCommandHandler;
	
	@Inject
	RemoveEmpAvaOrMapCommandHandler removeEmpAvaOrMapCommandHandler;
	
	@Inject
	RemoveDocumentFileEmpCommandHandler removeDocFileCommandHandler;
	
	@Inject
	UpdateCtgDocFileCommandHandler updateCtgDocumentFile;;
	/**
	 * Gets employee file management by employeeId.
	 *
	 * @return employee file management
	 */
	//vinhpx: start
	@POST
	@Path("find/getAvaOrMap/{employeeId}/{fileType}")
	public EmployeeFileManagementDto getAvaOrMap(@PathParam("employeeId") String employeeId, @PathParam("fileType") int fileType) {
		return this.employeeFileManagementFinder.getAvaOrMap(employeeId, fileType);
	}
	
	@POST
	@Path("command/insertAvaOrMap")
	public void insertAvaOrMap(EmpAvaOrMapCommand command) {
		this.addEmpAvaOrMapCommandHandler.handle(command);
	}
	
	@POST
	@Path("command/updateAvaOrMap")
	public void updateAvaOrMap(EmpAvaOrMapCommand command) {
		this.updateEmpAvaOrMapCommandHandler.handle(command);
	}
	
	@POST
	@Path("command/removeAvaOrMap")
	public void removeAvaOrMap(EmpAvaOrMapCommand command) {
		this.removeEmpAvaOrMapCommandHandler.handle(command);
	}
	
	@POST
	@Path("find/checkEmpFileMnExist/{employeeId}/{fileType}")
	public boolean checkEmpFileMnExist(@PathParam("employeeId") String employeeId, @PathParam("fileType") int fileType) {
		return this.employeeFileManagementFinder.checkEmpFileMnExist(employeeId, fileType);
	}
	//vinhpx: end

	@POST
	@Path("getlistdocfile/{employeeId}")
	public List<EmployeeFileManagementDto> getListDocumentFile(@PathParam("employeeId") String employeeId) {
		return this.employeeFileManagementFinder.getListDocumentFile(employeeId);
	}

	@POST
	@Path("savedocfile")
	public void saveDocumentFile(AddEmpDocumentFileCommand command) {
		this.empDocumentFileCommandHandler.handle(command);
	}
	
	@POST
	@Path("updatedata")
	public void updateDocumentFile(AddEmpDocumentFileCommand command) {
		this.empDocumentFileCommandHandler.handle(command);
	}
	
	@POST
	@Path("deletedata/{fileid}")
	public void deleteDocumentFile(@PathParam("fileid") String fileid) {
		this.removeDocFileCommandHandler.handle(fileid);
	}
	
	@POST
	@Path("updatectgdocfile")
	public void updateCtgForDocFile(UpdateCtgDocFileDocumentFileCommand command) {
		this.updateCtgDocumentFile.handle(command);
		System.out.println(command);
	}
}
