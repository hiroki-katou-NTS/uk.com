/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.exio.ws.exi.execlog;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacErrorLogDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacErrorLogFinder;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacExeResultLogDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacExeResultLogFinder;
import nts.uk.ctx.exio.dom.exi.execlog.ExecLogAsposeExportService;
import nts.uk.shr.sample.report.app.export.sample.SampleReportQuery;

/**
 * The Class WorkplaceConfigInfoWebService
 */
@Path("exio/exi/execlog")
@Produces("application/json")
public class ExiExecLogWebService extends WebService {
	@Inject
	private ExacExeResultLogFinder exacExeResultLogFinder;
	
	@Inject
	private ExacErrorLogFinder exacErrorLogFinder;
	
	/** The export service. */
    //@Inject
    //private ExiExecLogExportService exportService;
    @Inject
	private ExecLogAsposeExportService exportService;
    
    /**
	 * @param externalProcessId
	 * @return
	 */
	@Path("getLogResults")
	@POST
	public List<ExacExeResultLogDto> getLogResults(String externalProcessId) {
		return this.exacExeResultLogFinder.getExacExeResultLogByProcessId(externalProcessId);
	}
	
	@Path("getErrorLogs")
	@POST
	public List<ExacErrorLogDto> getExacErrorLogByProcessId(String externalProcessId) {
    	return this.exacErrorLogFinder.getExacErrorLogByProcessId(externalProcessId);
    }
	
    /**
     * @param processId
     * @return
     */
    /*@POST
    @Path("export/{processId}")
    public ExportServiceResult exportCsvError(@PathParam("processId") String processId) {
        return this.exportService.start(processId);
    }*/
    @POST
	@Path("generateCSV")
	public ExportServiceResult generate(SampleReportQuery query) {
		return this.exportService.start(query);
	}
}
