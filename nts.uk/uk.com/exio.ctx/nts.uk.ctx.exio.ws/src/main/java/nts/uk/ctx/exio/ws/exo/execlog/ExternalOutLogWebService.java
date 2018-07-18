package nts.uk.ctx.exio.ws.exo.execlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExOutOpMngCommandDelete;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExterOutExecLogCommand;
import nts.uk.ctx.exio.app.command.exo.executionlog.RemoveExOutOpMngCommandHandler;
import nts.uk.ctx.exio.app.command.exo.executionlog.UpdateExterOutExecLogCommandHandler;
import nts.uk.ctx.exio.app.command.exo.executionlog.UpdateExterOutExecLogUseDeleteFileCommandHandler;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogExportService;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ErrorContentDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutOpMngDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutOpMngFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExterOutExecLogDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExterOutExecLogFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("exio/exo/execlog")
@Produces("application/json")
public class ExternalOutLogWebService extends WebService {

	@Inject
	private ExternalOutLogFinder externalOutLogFinder;

	@Inject
	private ExternalOutLogExportService exportService;

	@Inject
	private UpdateExterOutExecLogUseDeleteFileCommandHandler updateExterOutExecLogUseDeleteFileCommandHandler;
	
	@Inject
	private ExOutOpMngFinder exOutOpMngFinder;
	
	@Inject
	private RemoveExOutOpMngCommandHandler removeExOutOpMngCommandHandler;
	
	@Inject
	private UpdateExterOutExecLogCommandHandler updateExterOutExecLogCommandHandler;
	
	@Inject
	private ExterOutExecLogFinder exterOutExecLogFinder;
	
	@Path("getExternalOutLog/{storeProcessingId}")
	@POST
	public List<ExternalOutLogDto> getExternalOutLogById(@PathParam("storeProcessingId") String storeProcessingId) {
		return this.externalOutLogFinder.getExternalOutLogById(storeProcessingId);
	}

	@POST
	@Path("export")
	public ExportServiceResult exportCsvError(ErrorContentDto command) {
		return this.exportService.start(command);
	}

	@POST
	@Path("useDeleteFile/{outProcessId}")
	public Integer useDeleteFile(@PathParam("outProcessId") String outProcessId) {
		return this.updateExterOutExecLogUseDeleteFileCommandHandler.handle(outProcessId);
	}
	
	@POST
	@Path("findExOutOpMng/{storeProcessingId}")
	public ExOutOpMngDto findExOutOpMng(@PathParam("storeProcessingId") String storeProcessingId) {
		return exOutOpMngFinder.getExOutOpMngById(storeProcessingId);
	}
	
	@POST
	@Path("deleteexOutOpMng")
	public void deleteexOutOpMng(ExOutOpMngCommandDelete command) {
		this.removeExOutOpMngCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateFileSize/{storeProcessingId}/{fileId}")
	public void updateFileSize( @PathParam("storeProcessingId") String storeProcessingId,
			@PathParam("fileId") String fileId) {
		String companyId = AppContexts.user().companyId();
		ExterOutExecLogCommand command = new ExterOutExecLogCommand(companyId, storeProcessingId, fileId);
		updateExterOutExecLogCommandHandler.handle(command);
	}
	
	@Path("getExterOutExecLog/{exterOutExecLogProcessId}")
	@POST
	public ExterOutExecLogDto getExterOutExecLogById(@PathParam("exterOutExecLogProcessId") String exterOutExecLogProcessId) {
		return this.exterOutExecLogFinder.getExterOutExecLogById(exterOutExecLogProcessId);
	}

}
