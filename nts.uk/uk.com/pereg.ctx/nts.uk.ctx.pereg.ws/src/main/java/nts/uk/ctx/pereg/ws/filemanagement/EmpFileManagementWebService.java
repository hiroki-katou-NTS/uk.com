package nts.uk.ctx.pereg.ws.filemanagement;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;
import nts.uk.ctx.pereg.app.command.filemanagement.AddEmpAvaOrMapCommandHandler;
import nts.uk.ctx.pereg.app.command.filemanagement.AddEmpDocumentFileCommand;
import nts.uk.ctx.pereg.app.command.filemanagement.EmpAvaOrMapCommand;
import nts.uk.ctx.pereg.app.command.filemanagement.EmpDocumentFileCommandHandler;
import nts.uk.ctx.pereg.app.command.filemanagement.RemoveDocumentFileCommand;
import nts.uk.ctx.pereg.app.command.filemanagement.RemoveDocumentFileEmpCommandHandler;
import nts.uk.ctx.pereg.app.command.filemanagement.RemoveEmpAvaOrMapCommandHandler;
import nts.uk.ctx.pereg.app.command.filemanagement.UpdateCtgDocFileCommandHandler;
import nts.uk.ctx.pereg.app.command.filemanagement.UpdateCtgDocFileDocumentFileCommand;
import nts.uk.ctx.pereg.app.command.filemanagement.UpdateEmpAvaOrMapCommandHandler;
import nts.uk.ctx.pereg.app.find.filemanagement.EmployeeFileManagementFinder;

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
	@Path("deletedata")
	public void deleteDocumentFile(RemoveDocumentFileCommand command) {
		this.removeDocFileCommandHandler.handle(command);
	}
	
	@POST
	@Path("updatectgdocfile")
	public void updateCtgForDocFile(UpdateCtgDocFileDocumentFileCommand command) {
		this.updateCtgDocumentFile.handle(command);
	}
}
