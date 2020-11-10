package nts.uk.ctx.sys.assist.ws.logdataresult;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.find.logdataresult.LogDataResultDto;
import nts.uk.ctx.sys.assist.app.find.logdataresult.LogDataResultFinder;
import nts.uk.ctx.sys.assist.app.find.logdataresult.exportcsv.LogDataParamsExport;
import nts.uk.ctx.sys.assist.app.find.logdataresult.exportcsv.LogDataResultExportService;
import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;

@Path("ctx/sys/assist/app")
@Produces("application/json")
public class LogDataResultWebService extends WebService {

	@Inject
	LogDataResultFinder logDataResultFinder;
	
	@Inject
	private LogDataResultExportService exportService;
	
	@POST
	@Path("getLogDataResults")
	public List<LogDataResultDto> getLogDataResults(LogDataParams logDataParams) {
		return logDataResultFinder.getLogDataResult(logDataParams);
	}
	
	@POST
	@Path("export-csv")
	public ExportServiceResult generate(LogDataParamsExport query) {
		return this.exportService.start(query);
	}
}
