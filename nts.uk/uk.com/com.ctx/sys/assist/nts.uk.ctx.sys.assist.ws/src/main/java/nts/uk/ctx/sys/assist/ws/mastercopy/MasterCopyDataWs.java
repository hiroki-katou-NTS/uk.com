package nts.uk.ctx.sys.assist.ws.mastercopy;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyDataCommand;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyDataCommandHanlder;
import nts.uk.ctx.sys.assist.app.command.mastercopy.MasterCopyDataExecutionRespone;
import nts.uk.ctx.sys.assist.app.export.mastercopy.error.MasterCopyExportErrorService;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.app.command.mastercopy.ErrorContentDto;

/**
 * The Class MasterCopyDataWs.
 */
@Path("sys/assist/mastercopy/data")
@Produces(MediaType.APPLICATION_JSON)
public class MasterCopyDataWs extends WebService{
	
	/** The async handler. */
	@Inject
	private MasterCopyDataCommandHanlder asyncHandler;
	
    /** The export service. */
    @Inject
    private MasterCopyExportErrorService exportService;
    
    @Inject
    private MasterCopyDataRepository repo;
	
	/**
	 * Execute master copy data.
	 *
	 * @param command the command
	 */
	@POST
	@Path("execute")
	public MasterCopyDataExecutionRespone executeMasterCopyData(MasterCopyDataCommand command){
		AsyncTaskInfo taskInfor = this.asyncHandler.handle(command);
		MasterCopyDataExecutionRespone response = new MasterCopyDataExecutionRespone();
		response.setTaskInfo(taskInfor);
		return response;
	}
	
	/**
	 * Interrupt.
	 *
	 * @param interrupt the execution
	 */
	@POST
	@Path("interrupt")
	public void interrupt() {
		this.asyncHandler.interrupt();
	}
	
	@POST
	@Path("log/export")
	public ExportServiceResult exportErrorCsv(List<ErrorContentDto> command) {
		return this.exportService.start(command);
	}
}
