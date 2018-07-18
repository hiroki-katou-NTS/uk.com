/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.exio.ws.exi.execlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExOutOpMngCommand;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExterOutExecLogCommand;
import nts.uk.ctx.exio.app.command.exo.executionlog.RemoveExOutOpMngCommandHandler;
import nts.uk.ctx.exio.app.command.exo.executionlog.UpdateExterOutExecLogCommandHandler;
import nts.uk.ctx.exio.app.find.exi.execlog.ErrorContentDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacErrorLogDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacErrorLogFinder;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacExeResultLogDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacExeResultLogFinder;
import nts.uk.ctx.exio.app.find.exi.execlog.ExiExecLogExportService;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutOpMngDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutOpMngFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExterOutExecLogDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExterOutExecLogFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ExiExecLogWebService
 */
@Path("exio/exi/execlog")
@Produces("application/json")
public class ExiExecLogWebService extends WebService {
	@Inject
	private ExacExeResultLogFinder exacExeResultLogFinder;

	@Inject
	private ExacErrorLogFinder exacErrorLogFinder;

	@Inject
	private ExiExecLogExportService exportService;
	
	/**
	 * @param externalProcessId
	 * @return
	 */
	@Path("getLogResults/{processId}")
	@POST
	public List<ExacExeResultLogDto> getLogResults(@PathParam("processId") String externalProcessId) {
		return this.exacExeResultLogFinder.getExacExeResultLogByProcessId(externalProcessId);
	}

	@Path("getErrorLogs/{processId}")
	@POST
	public List<ExacErrorLogDto> getExacErrorLogByProcessId(@PathParam("processId") String externalProcessId) {
		return this.exacErrorLogFinder.getExacErrorLogByProcessId(externalProcessId);
	}

	@POST
	@Path("export")
	public ExportServiceResult exportCsvError(ErrorContentDto command) {
		return this.exportService.start(command);
	}
}
